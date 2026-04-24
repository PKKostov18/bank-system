import { httpClient } from './http';
import type { AccountOpeningResponse, AccountStatusResponse } from '../types/auth';

export async function getCustomerAccountStatus(): Promise<AccountStatusResponse> {
  const response = await httpClient.get<AccountStatusResponse>('/api/customer/accounts/status');
  return response.data;
}

export async function openCustomerAccount(): Promise<AccountOpeningResponse> {
  const response = await httpClient.post<AccountOpeningResponse>('/api/customer/accounts/open');
  return response.data;
}

