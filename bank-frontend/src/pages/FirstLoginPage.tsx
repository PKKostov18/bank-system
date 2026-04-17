import { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { changePassword } from '../api/auth';
import { extractApiErrorMessage } from '../api/http';
import { getSession, markFirstLoginDone } from '../utils/authStorage';

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
    <main className="page page-center">
      <section className="card auth-card">
        <h1>First Login</h1>
        <p>For security reasons, you must replace your temporary password with a personal one.</p>

        <form onSubmit={handleSubmit} className="form-grid">
          <label>
            Current password
            <input
              type="password"
              value={currentPassword}
              onChange={(event) => setCurrentPassword(event.target.value)}
              required
              autoComplete="current-password"
            />
          </label>

          <label>
            New password
            <input
              type="password"
              value={newPassword}
              onChange={(event) => setNewPassword(event.target.value)}
              required
              autoComplete="new-password"
              minLength={8}
            />
          </label>

          <label>
            Confirm new password
            <input
              type="password"
              value={confirmPassword}
              onChange={(event) => setConfirmPassword(event.target.value)}
              required
              autoComplete="new-password"
              minLength={8}
            />
          </label>

          {error && <p className="error-text">{error}</p>}

          <button className="btn btn-primary" type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'Saving...' : 'Change password'}
          </button>
        </form>
      </section>
    </main>
  );
}

