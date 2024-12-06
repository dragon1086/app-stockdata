import React from 'react';
import { useCompanyAutocomplete } from '../../hooks/useCompanyAutocomplete';

interface CompanyNameInputProps {
  value: string;
  onChange: (value: string) => void;
  disabled: boolean;
  onToggleDisabled: () => void;
}

export const CompanyNameInput: React.FC<CompanyNameInputProps> = ({
  value,
  onChange,
  disabled,
  onToggleDisabled,
}) => {
  const { suggestions, loading, error, searchCompanies } = useCompanyAutocomplete();

  return (
    <div className="mb-4">
      <label htmlFor="companyNameInput" className="form-label d-flex align-items-center">
        기업 이름
        <i className="bi bi-question-circle-fill ms-2" data-bs-toggle="tooltip" data-bs-placement="right" 
           title="시뮬레이션할 기업을 선택합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다." />
      </label>
      <div className="input-group">
        <input
          type="text"
          id="companyNameInput"
          className={`form-control ${error ? 'is-invalid' : ''}`}
          value={value}
          onChange={(e) => {
            onChange(e.target.value);
            searchCompanies(e.target.value);
          }}
          disabled={disabled}
          placeholder="기업 이름"
          list="companySuggestions"
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
      {loading && <div className="text-muted mt-1">검색중...</div>}
      {error && <div className="invalid-feedback">{error}</div>}
      <datalist id="companySuggestions">
        {suggestions.map((company) => (
          <option key={company} value={company} />
        ))}
      </datalist>
    </div>
  );
};