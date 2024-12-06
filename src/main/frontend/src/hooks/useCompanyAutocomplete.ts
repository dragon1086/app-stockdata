import { useState } from 'react';
import { dealTrainingApi } from '../api/dealTrainingApi';

export const useCompanyAutocomplete = () => {
  const [suggestions, setSuggestions] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const searchCompanies = async (keyword: string) => {
    if (!keyword) {
      setSuggestions([]);
      return;
    }

    try {
      setLoading(true);
      setError(null);
      const companies = await dealTrainingApi.getCompanySuggestions(keyword);
      setSuggestions(companies);
    } catch (err) {
      setError('회사 검색 중 오류가 발생했습니다.');
      setSuggestions([]);
    } finally {
      setLoading(false);
    }
  };

  return {
    suggestions,
    loading,
    error,
    searchCompanies,
  };
};