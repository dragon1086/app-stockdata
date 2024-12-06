import React, { useState } from 'react';
import { CompanyNameInput } from '../../components/DealTraining/CompanyNameInput';
import { useDealTraining } from '../../hooks/useDealTraining';
import { DealTrainingForm, DifficultyLevel } from '../../types/dealTraining';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';

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

export const DealTrainingPage: React.FC = () => {
  const [isCompanyNameEnabled, setIsCompanyNameEnabled] = useState(false);
  const [isStartDateEnabled, setIsStartDateEnabled] = useState(false);
  const [isValuationEnabled, setIsValuationEnabled] = useState(false);

  const { loading, error, submitForm } = useDealTraining();

  const { register, handleSubmit, watch, setValue, formState: { errors } } = useForm<DealTrainingForm>({
    resolver: yupResolver(validationSchema),
    context: {
      isCompanyNameEnabled,
      isStartDateEnabled,
      isValuationEnabled,
    },
  });

  const onSubmit = (data: DealTrainingForm) => {
    submitForm(data);
  };

  return (
    <div className="container main-container">
      <h1 className="text-center mb-4">주식 매매 시뮬레이션</h1>

      <form onSubmit={handleSubmit(onSubmit)}>
        <CompanyNameInput
          value={watch('companyName')}
          onChange={(value) => setValue('companyName', value)}
          disabled={!isCompanyNameEnabled}
          onToggleDisabled={() => setIsCompanyNameEnabled(!isCompanyNameEnabled)}
        />

        {/* Other form fields will go here */}
        
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

      {error && (
        <div className="alert alert-danger mt-3">
          {error}
        </div>
      )}
    </div>
  );
};