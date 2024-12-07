import React from 'react';
import { HelpCircle } from 'lucide-react';
import { Tooltip, OverlayTrigger } from 'react-bootstrap';

interface AutocompleteInputProps {
  label: string;
  tooltipText: string;
  value: string;
  onChange: (value: string) => void;
  suggestions: string[];
  placeholder?: string;
  disabled?: boolean;
}

export const AutocompleteInput: React.FC<AutocompleteInputProps> = ({
  label,
  tooltipText,
  value,
  onChange,
  suggestions,
  placeholder,
  disabled = false,
}) => {
  const tooltipContent = (
    <Tooltip id={`${label}-tooltip`}>{tooltipText}</Tooltip>
  );

  return (
    <div className="mb-4">
      <label className="form-label d-flex align-items-center">
        {label}
        <OverlayTrigger placement="right" overlay={tooltipContent}>
          <span className="ms-2">
            <HelpCircle size={16} className="text-primary cursor-pointer" />
          </span>
        </OverlayTrigger>
      </label>
      <input
        type="text"
        className="form-control"
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder={placeholder}
        list={`${label}-suggestions`}
        disabled={disabled}
      />
      <datalist id={`${label}-suggestions`}>
        {suggestions.map((suggestion) => (
          <option key={suggestion} value={suggestion} />
        ))}
      </datalist>
    </div>
  );
};