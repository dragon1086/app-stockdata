export type DifficultyLevel = 'beginner' | 'intermediate' | 'master';

export interface DealTrainingForm {
  companyName: string;
  slotAmount: number;
  portion: number;
  startDate: string;
  valuationPercent: number;
  level: DifficultyLevel;
}

export interface SessionUser {
  email: string;
}

export interface CompanyAutocompleteResponse {
  companies: string[];
}

export interface DealCalculationResponse {
  success: boolean;
  message?: string;
  redirectUrl?: string;
}