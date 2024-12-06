import React, { useState, useEffect } from 'react';
import { HelpCircle } from 'lucide-react';
import { CompanyNameInput } from '../../components/DealTraining/CompanyNameInput';
import { AmountInput } from '../../components/DealTraining/AmountInput';
import { PortionInput } from '../../components/DealTraining/PortionInput';
import { DateInput } from '../../components/DealTraining/DateInput';
import { ValuationInput } from '../../components/DealTraining/ValuationInput';
import { LevelSelect } from '../../components/DealTraining/LevelSelect';
import { useDealTraining } from '../../hooks/useDealTraining';
import { DealTrainingForm } from '../../types/dealTraining';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Tooltip } from 'bootstrap';

const validationSchema = yup.object().shape({
  companyName: yup.string().when('isCompanyNameEnabled', {
    is: true,
    then: yup.string().required('기업 이름을 입력해주세요'),
  }),
  slotAmount: yup.number()
    .required('배분할 금액을 입력해주세요')
    .positive('금액은 양수여야 합니다'),
  portion: yup.number()
    .required('시작 비중을 입력해주세요')
    .min(0, '비중은 0 이상이어야 합니다')
    .max(100, '비중은 100 이하여야 합니다'),
  startDate: yup.string().when('isStartDateEnabled', {
    is: true,
    then: yup.string().required('시작 날짜를 선택해주세요'),
  }),
  valuationPercent: yup.number().when('isValuationEnabled', {
    is: true,
    then: yup.number()
      .required('평가손익을 입력해주세요')
      .min(-100, '평가손익은 -100 이상이어야 합니다')
      .max(100, '평가손익은 100 이하여야 합니다'),
  }),
  level: yup.string().required('난이도를 선택해주세요'),
});

interface SessionUser {
  email: string;
}

const DealTraining: React.FC = () => {
  const [isCompanyNameEnabled, setIsCompanyNameEnabled] = useState(false);
  const [isStartDateEnabled, setIsStartDateEnabled] = useState(false);
  const [isValuationEnabled, setIsValuationEnabled] = useState(false);
  const [sessionUser, setSessionUser] = useState<SessionUser | null>(null);

  const { loading, error, submitForm, openHistory } = useDealTraining();
  const { register, handleSubmit, watch, setValue, formState: { errors } } = useForm<DealTrainingForm>({
    resolver: yupResolver(validationSchema),
    context: {
      isCompanyNameEnabled,
      isStartDateEnabled,
      isValuationEnabled,
    },
  });

  useEffect(() => {
    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.forEach(tooltipTriggerEl => {
      new Tooltip(tooltipTriggerEl);
    });
  }, []);

  const onSubmit = (data: DealTrainingForm) => {
    submitForm(data);
  };

  return (
    <>
      <nav className="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
        <div className="container">
          <a className="navbar-brand" href="/">주식 매매 시뮬레이션</a>
          <button 
            className="navbar-toggler" 
            type="button" 
            data-bs-toggle="collapse" 
            data-bs-target="#navbarNav"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <a className="btn btn-outline-light" href="/buildup">
                  자동 시뮬레이션으로 이동
                </a>
              </li>
            </ul>
            <div className="ms-auto">
              {sessionUser ? (
                <>
                  <span className="text-light me-3">
                    환영합니다, {sessionUser.email}님!
                  </span>
                  <button 
                    className="btn btn-outline-light me-2"
                    onClick={() => window.location.href = '/logout/google'}
                  >
                    로그아웃
                  </button>
                  <button 
                    className="btn btn-info"
                    onClick={openHistory}
                  >
                    시뮬레이션 이어하기
                  </button>
                </>
              ) : (
                <button 
                  className="btn btn-outline-light"
                  onClick={() => window.location.href = '/login/google'}
                >
                  Google로 로그인
                </button>
              )}
            </div>
          </div>
        </div>
      </nav>

      <div className="container main-container mt-5 py-4">
        <h1 className="text-center mb-4">주식 매매 시뮬레이션</h1>

        {error && (
          <div className="alert alert-danger mb-4">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit(onSubmit)} className="mt-5">
          <CompanyNameInput
            value={watch('companyName')}
            onChange={(value) => setValue('companyName', value)}
            disabled={!isCompanyNameEnabled}
            onToggleDisabled={() => setIsCompanyNameEnabled(!isCompanyNameEnabled)}
          />

          <AmountInput
            register={register}
            error={errors.slotAmount}
          />

          <PortionInput
            register={register}
            error={errors.portion}
          />

          <DateInput
            register={register}
            error={errors.startDate}
            disabled={!isStartDateEnabled}
            onToggleDisabled={() => setIsStartDateEnabled(!isStartDateEnabled)}
          />

          <ValuationInput
            register={register}
            error={errors.valuationPercent}
            disabled={!isValuationEnabled}
            onToggleDisabled={() => setIsValuationEnabled(!isValuationEnabled)}
          />

          <LevelSelect
            register={register}
            error={errors.level}
          />

          <div className="d-grid">
            <button 
              type="submit" 
              className="btn btn-primary btn-lg"
              disabled={loading}
            >
              {loading ? '처리중...' : '시뮬레이션 시작'}
            </button>
          </div>
        </form>

        <hr className="my-4" />

        <div className="text-center">
          <button 
            type="button" 
            className="btn btn-info"
            onClick={() => window.location.href = '/dealTrainingManual'}
          >
            시뮬레이션 메뉴얼
          </button>
        </div>
      </div>
    </>
  );
};

export default DealTraining;