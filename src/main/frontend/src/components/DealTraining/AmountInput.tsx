import React from 'react';
import { UseFormRegister, FieldError } from 'react-hook-form';
import { DealTrainingForm } from '../../types/dealTraining';

interface AmountInputProps {
  register: UseFormRegister<DealTrainingForm>;
  error?: FieldError;
}

export const AmountInput: React.FC<AmountInputProps> = ({ register, error }) => {
  return (
    <div className="mb-4">
      <label htmlFor="slotAmount" className="form-label d-flex align-items-center">
        종목에 배분할 금액
        <i className="bi bi-question-circle-fill ms-2" 
           data-bs-toggle="tooltip" 
           data-bs-placement="right"
           title="이 종목에 투자할 총 금액을 입력합니다. 이 금액을 기준으로 매매가 이루어집니다." />
      </label>
      <input
        type="number"
        className={`form-control ${error ? 'is-invalid' : ''}`}
        id="slotAmount"
        placeholder="배분할 총금액을 입력하세요"
        {...register('slotAmount')}
      />
      {error && <div className="invalid-feedback">{error.message}</div>}
    </div>
  );
};