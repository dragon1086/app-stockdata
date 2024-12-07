export type DifficultyLevel = 'beginner' | 'intermediate' | 'master';

export interface DealTrainingForm {
  companyName: string;
  slotAmount: number;
  portion: number;
  startDate: string;
  valuationPercent: number;
  level: DifficultyLevel;
  isCompanyNameEnabled?: boolean;
  isStartDateEnabled?: boolean;
  isValuationEnabled?: boolean;
}