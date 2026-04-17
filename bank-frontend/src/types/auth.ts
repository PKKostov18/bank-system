export type UserRole = 'CUSTOMER' | 'EMPLOYEE';

export interface AuthResponse {
  token: string;
  customerId: number;
  email: string;
  role: UserRole;
  firstLogin: boolean;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface ChangePasswordRequest {
  currentPassword: string;
  newPassword: string;
}

export interface ApiMessage {
  message: string;
}

export interface AuthSession {
  token: string;
  customerId: number;
  email: string;
  role: UserRole;
  firstLogin: boolean;
}

export interface IndividualOnboardingRequest {
  firstName: string;
  lastName: string;
  egn: string;
  email: string;
}

export interface CorporateOnboardingRequest {
  companyName: string;
  eik: string;
  representativeFirstName: string;
  representativeLastName: string;
  email: string;
}

export interface OnboardingResponse {
  customerId: number;
  email: string;
  customerType: 'INDIVIDUAL' | 'CORPORATE';
  temporaryPasswordSent: boolean;
  emailDeliveryChannel: 'LOCAL_MAILHOG' | 'EXTERNAL_SMTP';
  emailRelay: string;
}
