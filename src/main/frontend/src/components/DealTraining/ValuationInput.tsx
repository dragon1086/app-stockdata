import React from 'react';
import { UseFormRegister, FieldError } from 'react-hook-form';
import { DealTrainingForm } from '../../types/dealTraining';

interface ValuationInputProps {
  register: UseFormRegister<DealTrainingForm>;
  error?: FieldError;
  disabled: boolean;
  onToggleDisabled: () => void;
}

export const ValuationInput: React.FC<ValuationInputProps> = ({
  register,
  error,
  disabled,
  onToggleDisabled
}) => {
  return (
    <div className="mb-4">
      <label htmlFor="valuationPercent" className="form-label d-flex align-items-center">
        시작 평가손익(%)
        <i className="bi bi-question-circle-fill ms-2" 
           data-bs-toggle="tooltip" 
           data-bs-placement="right"
           title="초기 평가손익 상태를 설정합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다." />
      </label>
      <div className="input-group">
        <input
          type="number"
          className={`form-control ${error ? 'is-invalid' : ''}`}
          id="valuationPercent"
          placeholder="초기 평가손익"
          disabled={disabled}
          {...register('valuationPercent')}
        />
        <div className="input-group-text">
          <input
            className="form-check-input mt-0"
            type="checkbox"
            checked={!disabled}
            onChange={onToggleDisabled}
          />
        </div>
      </div>
      {error && <div className="invalid-feedback">{error.message}</div>}
    </div>
  );
};