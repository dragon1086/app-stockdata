export interface CompanyAutocompleteResponse {
  companies: string[];
}

export interface DealCalculationResponse {
  success: boolean;
  message?: string;
  redirectUrl?: string;
}

export interface ApiError {
  message: string;
  status?: number;
}