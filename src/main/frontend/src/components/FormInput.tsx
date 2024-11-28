import React from 'react';
import { QuestionCircle } from 'lucide-react';

interface FormInputProps {
  id: string;
  label: string;
  tooltip?: string;
  type?: string;
  value: string | number;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  disabled?: boolean;
  hasCheckbox?: boolean;
  isChecked?: boolean;
  onCheckboxChange?: () => void;
}

const FormInput: React.FC<FormInputProps> = ({
  id,
  label,
  tooltip,
  type = 'text',
  value,
  onChange,
  placeholder,
  disabled,
  hasCheckbox,
  isChecked,
  onCheckboxChange
}) => {
  return (
    <div className="mb-6">
      <div className="flex items-center justify-between mb-2">
        <label htmlFor={id} className="flex items-center text-gray-700 font-semibold">
          {label}
          {tooltip && (
            <div className="group relative ml-2">
              <QuestionCircle className="h-5 w-5 text-blue-500 cursor-help" />
              <div className="hidden group-hover:block absolute z-10 w-64 p-2 bg-gray-800 text-white text-sm rounded shadow-lg -right-2 top-6">
                {tooltip}
              </div>
            </div>
          )}
        </label>
        {hasCheckbox && (
          <div className="flex items-center">
            <input
              type="checkbox"
              checked={isChecked}
              onChange={onCheckboxChange}
              className="form-checkbox h-5 w-5 text-blue-500"
            />
            <span className="ml-2 text-sm text-gray-600">직접 입력</span>
          </div>
        )}
      </div>
      <input
        type={type}
        id={id}
        name={id}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        disabled={disabled}
        className="w-full px-4 py-2 border rounded-lg bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:bg-gray-100"
      />
    </div>
  );
};

export default FormInput;