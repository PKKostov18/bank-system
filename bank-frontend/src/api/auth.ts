import { httpClient } from './http';
import type { ApiMessage, AuthResponse, ChangePasswordRequest, LoginRequest } from '../types/auth';

export async function login(request: LoginRequest): Promise<AuthResponse> {
  const response = await httpClient.post<AuthResponse>('/api/auth/login', request);
  return response.data;
}

export async function changePassword(request: ChangePasswordRequest): Promise<ApiMessage> {
  const response = await httpClient.post<ApiMessage>('/api/auth/change-password', request);
  return response.data;
}

