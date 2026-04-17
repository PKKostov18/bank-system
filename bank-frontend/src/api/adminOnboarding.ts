import { httpClient } from './http';
import type {
  CorporateOnboardingRequest,
  IndividualOnboardingRequest,
  OnboardingResponse,
} from '../types/auth';

const ADMIN_SECRET_HEADER = 'X-Admin-Secret';

export async function createIndividualCustomer(
  adminSecret: string,
  request: IndividualOnboardingRequest,
): Promise<OnboardingResponse> {
  const response = await httpClient.post<OnboardingResponse>(
    '/api/admin/secret/onboarding/individual',
    request,
    {
      headers: {
        [ADMIN_SECRET_HEADER]: adminSecret,
      },
    },
  );

  return response.data;
}

export async function createCorporateCustomer(
  adminSecret: string,
  request: CorporateOnboardingRequest,
): Promise<OnboardingResponse> {
  const response = await httpClient.post<OnboardingResponse>(
    '/api/admin/secret/onboarding/corporate',
    request,
    {
      headers: {
        [ADMIN_SECRET_HEADER]: adminSecret,
      },
    },
  );

  return response.data;
}

