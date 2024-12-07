import axios from 'axios';
import { DealTrainingForm } from '../types/dealTraining';
import { CompanyAutocompleteResponse, DealCalculationResponse } from '../types/api';

const api = axios.create({
  baseURL: '/',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const dealTrainingApi = {
  getCompanySuggestions: async (keyword: string): Promise<string[]> => {
    const response = await api.post<CompanyAutocompleteResponse>('/company', { keyword });
    return response.data.companies;
  },

  submitCalculation: async (data: DealTrainingForm): Promise<DealCalculationResponse> => {
    const response = await api.post<DealCalculationResponse>('/deal-calculate', data);
    return response.data;
  },

  getHistories: async () => {
    const response = await api.get('/deal-calculate-histories');
    return response.data;
  },
};