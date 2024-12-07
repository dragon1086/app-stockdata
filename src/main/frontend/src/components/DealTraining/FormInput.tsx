import React from 'react';
import { HelpCircle } from 'lucide-react';
import { UseFormRegister, FieldError } from 'react-hook-form';
import { DealTrainingForm } from '../../types/dealTraining';
import Tooltip from 'react-bootstrap/Tooltip';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';

interface FormInputProps {
  label: string;
  name: keyof DealTrainingForm;
  tooltipText: string;
  type?: string;
  placeholder?: string;
  register: UseFormRegister<DealTrainingForm>;
  error?: FieldError;
  disabled?: boolean;
}

export const FormInput: React.FC<FormInputProps> = ({
  label,
  name,
  tooltipText,
  type = 'text',
  placeholder,
  register,
  error,
  disabled = false,
}) => {
  const tooltipContent = (
    <Tooltip id={`${name}-tooltip`}>{tooltipText}</Tooltip>
  );

  return (
    <div className="mb-4">
      <label htmlFor={name} className="form-label d-flex align-items-center">
        {label}
        <OverlayTrigger placement="right" overlay={tooltipContent}>
          <span className="ms-2">
            <HelpCircle size={16} className="text-primary cursor-pointer" />
          </span>
        </OverlayTrigger>
      </label>
      <input
        type={type}
        id={name}
        className={"form-control" + (error ? " is-invalid" : "")}
        placeholder={placeholder}
        disabled={disabled}
        {...register(name)}
      />
      {error && <div className="invalid-feedback">{error.message}</div>}
    </div>
  );
};