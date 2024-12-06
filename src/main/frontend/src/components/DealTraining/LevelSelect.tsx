import React from 'react';
import { UseFormRegister, FieldError } from 'react-hook-form';
import { DealTrainingForm } from '../../types/dealTraining';

interface LevelSelectProps {
  register: UseFormRegister<DealTrainingForm>;
  error?: FieldError;
}

export const LevelSelect: React.FC<LevelSelectProps> = ({ register, error }) => {
  return (
    <div className="mb-4">
      <label htmlFor="level" className="form-label d-flex align-items-center">
        난이도 선택
        <i className="bi bi-question-circle-fill ms-2" 
           data-bs-toggle="tooltip" 
           data-bs-placement="right"
           title="시뮬레이션의 난이도를 선택합니다. 초급은 -20%~+20%, 중급은 -50%~+50%, 고급은 -80%~+80%의 범위에서 시작 평가손익이 설정됩니다." />
      </label>
      <select
        className={`form-select ${error ? 'is-invalid' : ''}`}
        id="level"
        {...register('level')}
      >
        <option value="beginner">초급 (-20%~+20%)</option>
        <option value="intermediate">중급 (-50%~+50%)</option>
        <option value="master">고급 (-80%~+80%)</option>
      </select>
      {error && <div className="invalid-feedback">{error.message}</div>}
    </div>
  );
};