export type DifficultyLevel = 'beginner' | 'intermediate' | 'master';

export interface DealTrainingFormBase {
  companyName: string;
  slotAmount: number;
  portion: number;
  startDate: string;
  valuationPercent: number;
  level: DifficultyLevel;
}

export interface DealTrainingForm extends Partial<Omit<DealTrainingFormBase, 'slotAmount' | 'portion' | 'level'>> {
  slotAmount: number;
  portion: number;
  level: DifficultyLevel;
}

export interface CompanyAutocompleteResponse {
  companies: string[];
}

export interface DealCalculationResponse {
  success: boolean;
  message?: string;
  redirectUrl?: string;
}

export interface SessionUser {
  email: string;
}