import React from 'react';
import { UseFormRegister, FieldError } from 'react-hook-form';
import { DealTrainingForm } from '../../types/dealTraining';

interface PortionInputProps {
  register: UseFormRegister<DealTrainingForm>;
  error?: FieldError;
}

export const PortionInput: React.FC<PortionInputProps> = ({ register, error }) => {
  return (
    <div className="mb-4">
      <label htmlFor="portion" className="form-label d-flex align-items-center">
        시작 비중(%)
        <i className="bi bi-question-circle-fill ms-2" 
           data-bs-toggle="tooltip" 
           data-bs-placement="right"
           title="초기 투자 비중을 설정합니다. 0%부터 100%까지 설정 가능하며, 0%는 현금 보유 상태, 100%는 풀 매수 상태를 의미합니다." />
      </label>
      <input
        type="number"
        className={`form-control ${error ? 'is-invalid' : ''}`}
        id="portion"
        placeholder="% 제외하고 입력하세요(소수점 제외)"
        {...register('portion')}
      />
      {error && <div className="invalid-feedback">{error.message}</div>}
    </div>
  );
};