import type { AuthResponse, AuthSession } from '../types/auth';

const AUTH_STORAGE_KEY = 'bank_auth_session';

export function saveSession(auth: AuthResponse): void {
  const session: AuthSession = {
    token: auth.token,
    customerId: auth.customerId,
    email: auth.email,
    role: auth.role,
    firstLogin: auth.firstLogin,
  };
  localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(session));
}

export function getSession(): AuthSession | null {
  const raw = localStorage.getItem(AUTH_STORAGE_KEY);
  if (!raw) {
    return null;
  }

  try {
    return JSON.parse(raw) as AuthSession;
  } catch {
    localStorage.removeItem(AUTH_STORAGE_KEY);
    return null;
  }
}

export function clearSession(): void {
  localStorage.removeItem(AUTH_STORAGE_KEY);
}

export function markFirstLoginDone(): void {
  const session = getSession();
  if (!session) {
    return;
  }

  saveSession({ ...session, firstLogin: false });
}

