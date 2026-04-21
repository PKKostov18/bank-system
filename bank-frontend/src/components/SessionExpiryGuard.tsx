import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { logoutIfSessionExpired } from '../utils/authStorage';

const SESSION_CHECK_INTERVAL_MS = 15000;
const PUBLIC_ROUTES = new Set(['/', '/login']);

export function SessionExpiryGuard() {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const checkSessionExpiry = () => {
      const hasExpired = logoutIfSessionExpired();
      if (hasExpired && !PUBLIC_ROUTES.has(location.pathname)) {
        navigate('/login', { replace: true });
      }
    };

    checkSessionExpiry();
    const intervalId = window.setInterval(checkSessionExpiry, SESSION_CHECK_INTERVAL_MS);

    return () => {
      window.clearInterval(intervalId);
    };
  }, [location.pathname, navigate]);

  return null;
}

