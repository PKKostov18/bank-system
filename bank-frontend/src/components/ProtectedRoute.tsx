import type { ReactElement } from 'react';
import { Navigate } from 'react-router-dom';
import type { UserRole } from '../types/auth';
import { getSession } from '../utils/authStorage';

interface ProtectedRouteProps {
  children: ReactElement;
  requireFirstLogin?: boolean;
  denyFirstLogin?: boolean;
  allowedRoles?: UserRole[];
}

export function ProtectedRoute({
  children,
  requireFirstLogin = false,
  denyFirstLogin = false,
  allowedRoles,
}: ProtectedRouteProps) {
  const session = getSession();

  if (!session) {
    return <Navigate to="/login" replace />;
  }

  if (requireFirstLogin && !session.firstLogin) {
    return <Navigate to="/app" replace />;
  }

  if (denyFirstLogin && session.firstLogin) {
    return <Navigate to="/first-login" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(session.role)) {
    if (session.role === 'EMPLOYEE') {
      return <Navigate to="/admin/secret-onboarding" replace />;
    }
    return <Navigate to={session.firstLogin ? '/first-login' : '/app'} replace />;
  }

  return children;
}

