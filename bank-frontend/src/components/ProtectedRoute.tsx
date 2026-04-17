import type { ReactElement } from 'react';
import { Navigate } from 'react-router-dom';
import { getSession } from '../utils/authStorage';

interface ProtectedRouteProps {
  children: ReactElement;
  requireFirstLogin?: boolean;
  denyFirstLogin?: boolean;
}

export function ProtectedRoute({ children, requireFirstLogin = false, denyFirstLogin = false }: ProtectedRouteProps) {
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

  return children;
}

