import React, { useState, useEffect, useRef } from 'react';
import { QuestionCircle } from 'lucide-react';

interface AutocompleteInputProps {
  id: string;
  label: string;
  tooltip?: string;
  value: string;
  onChange: (value: string) => void;
  disabled?: boolean;
  hasCheckbox?: boolean;
  isChecked?: boolean;
  onCheckboxChange?: () => void;
}

const AutocompleteInput: React.FC<AutocompleteInputProps> = ({
  id,
  label,
  tooltip,
  value,
  onChange,
  disabled,
  hasCheckbox,
  isChecked,
  onCheckboxChange
}) => {
  const [suggestions, setSuggestions] = useState<string[]>([]);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const wrapperRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const fetchSuggestions = async () => {
      if (value.length < 2 || disabled) return;

      try {
        const response = await fetch(`/api/companies/search?query=${value}`);
        if (response.ok) {
          const data = await response.json();
          setSuggestions(data);
          setShowSuggestions(true);
        }
      } catch (error) {
        console.error('Failed to fetch suggestions:', error);
      }
    };

    fetchSuggestions();
  }, [value, disabled]);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (wrapperRef.current && !wrapperRef.current.contains(event.target as Node)) {
        setShowSuggestions(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  return (
    <div className="mb-6" ref={wrapperRef}>
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
      <div className="relative">
        <input
          type="text"
          id={id}
          value={value}
          onChange={(e) => onChange(e.target.value)}
          disabled={disabled}
          className="w-full px-4 py-2 border rounded-lg bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:bg-gray-100"
          placeholder="기업명을 입력하세요"
        />
        {showSuggestions && suggestions.length > 0 && (
          <ul className="absolute z-10 w-full bg-white border rounded-lg mt-1 shadow-lg">
            {suggestions.map((suggestion, index) => (
              <li
                key={index}
                onClick={() => {
                  onChange(suggestion);
                  setShowSuggestions(false);
                }}
                className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              >
                {suggestion}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default AutocompleteInput;