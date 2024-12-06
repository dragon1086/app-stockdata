import React from 'react';
import { UseFormRegister, FieldError } from 'react-hook-form';
import { DealTrainingForm } from '../../types/dealTraining';

interface DateInputProps {
  register: UseFormRegister<DealTrainingForm>;
  error?: FieldError;
  disabled: boolean;
  onToggleDisabled: () => void;
}

export const DateInput: React.FC<DateInputProps> = ({ 
  register, 
  error, 
  disabled,
  onToggleDisabled
}) => {
  return (
    <div className="mb-4">
      <label htmlFor="startDate" className="form-label d-flex align-items-center">
        시작 날짜
        <i className="bi bi-question-circle-fill ms-2" 
           data-bs-toggle="tooltip" 
           data-bs-placement="right"
           title="시뮬레이션 시작 날짜를 선택합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다." />
      </label>
      <div className="input-group">
        <input
          type="date"
          className={`form-control ${error ? 'is-invalid' : ''}`}
          id="startDate"
          disabled={disabled}
          {...register('startDate')}
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