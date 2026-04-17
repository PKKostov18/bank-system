import { useState } from 'react';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { login } from '../api/auth';
import { extractApiErrorMessage } from '../api/http';
import { getSession, saveSession } from '../utils/authStorage';

export function LoginPage() {
  const existingSession = getSession();
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  if (existingSession) {
    return <Navigate to={existingSession.firstLogin ? '/first-login' : '/app'} replace />;
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setIsSubmitting(true);
    setError(null);

    try {
      const response = await login({ email, password });
      saveSession(response);
      navigate(response.firstLogin ? '/first-login' : '/app', { replace: true });
    } catch (submitError) {
      setError(extractApiErrorMessage(submitError, 'Login failed. Please check your credentials.'));
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <main className="page page-center">
      <section className="card auth-card">
        <h1>Online Banking Sign In</h1>
        <p>Use the email and temporary/personal password provided by the bank.</p>

        <form onSubmit={handleSubmit} className="form-grid">
          <label>
            Email
            <input
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              required
              autoComplete="email"
            />
          </label>

          <label>
            Password
            <input
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              required
              autoComplete="current-password"
            />
          </label>

          {error && <p className="error-text">{error}</p>}

          <button className="btn btn-primary" type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'Signing in...' : 'Sign in'}
          </button>
        </form>

        <Link className="link-muted" to="/">
          Back to home page
        </Link>
      </section>
    </main>
  );
}

