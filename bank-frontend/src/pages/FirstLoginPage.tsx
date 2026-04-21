import { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { changePassword } from '../api/auth';
import { extractApiErrorMessage } from '../api/http';
import { getSession, markFirstLoginDone } from '../utils/authStorage';
import '../index.css';

export function FirstLoginPage() {
  const session = getSession();
  const navigate = useNavigate();

  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  if (!session) {
    return <Navigate to="/login" replace />;
  }

  if (!session.firstLogin) {
    return <Navigate to="/app" replace />;
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    if (newPassword !== confirmPassword) {
      setError('New password and confirmation password must match.');
      return;
    }

    setIsSubmitting(true);
    setError(null);

    try {
      await changePassword({ currentPassword, newPassword });
      markFirstLoginDone();
      navigate('/app', { replace: true });
    } catch (submitError) {
      setError(extractApiErrorMessage(submitError, 'Password change failed.'));
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <div className="home-container">
      <div className="background-shapes">
        <div className="shape shape-1"></div>
        <div className="shape shape-2"></div>
      </div>

      <div className="glass-card auth-card auth-card-medium">
        <div className="brand-badge">First login security</div>
        <h1 className="main-title page-title">
          Change temporary password
        </h1>
        <p className="subtitle page-subtitle">
          For your security, set a personal password before continuing.
        </p>

        <form onSubmit={handleSubmit} className="auth-form-stack">
          <div className="form-field">
            <label className="form-label">Current password</label>
            <input
              type="password"
              value={currentPassword}
              onChange={(event) => setCurrentPassword(event.target.value)}
              required
              autoComplete="current-password"
              className="glass-input"
            />
          </div>

          <div className="form-field">
            <label className="form-label">New password</label>
            <input
              type="password"
              value={newPassword}
              onChange={(event) => setNewPassword(event.target.value)}
              required
              autoComplete="new-password"
              minLength={8}
              className="glass-input"
            />
          </div>

          <div className="form-field">
            <label className="form-label">Confirm new password</label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(event) => setConfirmPassword(event.target.value)}
              required
              autoComplete="new-password"
              minLength={8}
              className="glass-input"
            />
          </div>

          {error && <div className="status-banner status-banner-error">{error}</div>}

          <button className="btn-primary auth-submit-btn" type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'Saving...' : 'Update password'}
          </button>
        </form>
      </div>
    </div>
  );
}

