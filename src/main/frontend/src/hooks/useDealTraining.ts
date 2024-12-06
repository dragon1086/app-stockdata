import { useState } from 'react';
import { DealTrainingForm } from '../types/dealTraining';
import { dealTrainingApi } from '../api/dealTrainingApi';

export const useDealTraining = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const submitForm = async (formData: DealTrainingForm) => {
    try {
      setLoading(true);
      setError(null);
      const response = await dealTrainingApi.submitCalculation(formData);
      
      if (response.success && response.redirectUrl) {
        window.location.href = response.redirectUrl;
      } else if (!response.success && response.message) {
        setError(response.message);
      }
    } catch (err) {
      setError('시뮬레이션 시작 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  };

  const openHistory = () => {
    window.open('/deal-calculate-histories', '_blank');
  };

  return {
    loading,
    error,
    submitForm,
    openHistory,
  };
};