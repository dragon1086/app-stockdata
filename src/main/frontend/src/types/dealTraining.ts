export interface DealTrainingForm {
  companyName: string;
  slotAmount: number;
  portion: number;
  startDate: string;
  valuationPercent: number;
  level: DifficultyLevel;
}

export interface FormState {
  isCompanyNameEnabled: boolean;
  isStartDateEnabled: boolean;
  isValuationPercentEnabled: boolean;
}

export interface User {
  id: string;
  name: string;
  email: string;
}

export type DifficultyLevel = 'beginner' | 'intermediate' | 'master';

export interface TooltipContent {
  companyName: string;
  slotAmount: string;
  portion: string;
  startDate: string;
  valuationPercent: string;
  level: string;
}