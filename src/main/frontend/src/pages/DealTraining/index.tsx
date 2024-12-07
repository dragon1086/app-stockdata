import React, { useState, useEffect } from 'react';
import { HelpCircle } from 'lucide-react';
import { CompanyNameInput } from '../../components/DealTraining/CompanyNameInput';
import { AmountInput } from '../../components/DealTraining/AmountInput';
import { PortionInput } from '../../components/DealTraining/PortionInput';
import { DateInput } from '../../components/DealTraining/DateInput';
import { ValuationInput } from '../../components/DealTraining/ValuationInput';
import { LevelSelect } from '../../components/DealTraining/LevelSelect';
import { useDealTraining } from '../../hooks/useDealTraining';
import { DealTrainingForm, DifficultyLevel } from '../../types/dealTraining';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Tooltip } from 'bootstrap';

const validationSchema = yup.object({
  companyName: yup.string().when('$isCompanyNameEnabled', {
    is: true,
    then: () => yup.string().required('기업 이름을 입력해주세요')
  }),
  slotAmount: yup.number()
    .typeError('금액을 입력해주세요')
    .required('배분할 금액을 입력해주세요')
    .positive('금액은 양수여야 합니다'),
  portion: yup.number()
    .typeError('비중을 입력해주세요')
    .required('시작 비중을 입력해주세요')
    .min(0, '비중은 0 이상이어야 합니다')
    .max(100, '비중은 100 이하여야 합니다'),
  startDate: yup.string().when('$isStartDateEnabled', {
    is: true,
    then: () => yup.string().required('시작 날짜를 선택해주세요')
  }),
  valuationPercent: yup.number().when('$isValuationEnabled', {
    is: true,
    then: () => yup.number()
      .typeError('평가손익을 입력해주세요')
      .required('평가손익을 입력해주세요')
      .min(-100, '평가손익은 -100 이상이어야 합니다')
      .max(100, '평가손익은 100 이하여야 합니다')
  }),
  level: yup.string()
    .oneOf(['beginner', 'intermediate', 'master'] as DifficultyLevel[], '올바른 난이도를 선택해주세요')
    .required('난이도를 선택해주세요') as yup.StringSchema<DifficultyLevel>
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
    defaultValues: {
      companyName: '',
      slotAmount: undefined,
      portion: undefined,
      startDate: '',
      valuationPercent: undefined,
      level: 'beginner' as DifficultyLevel,
    }
  });

  useEffect(() => {
    // Initialize tooltips
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    tooltipTriggerList.forEach(element => {
      new Tooltip(element);
    });
  }, []);

  const onSubmit = (data: DealTrainingForm) => {
    submitForm(data);
  };

  // ... (나머지 JSX 코드는 그대로 유지)
};

export default DealTraining;