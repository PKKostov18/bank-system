import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createCorporateCustomer, createIndividualCustomer } from '../api/adminOnboarding';
import { extractApiErrorMessage } from '../api/http';
import type { OnboardingResponse } from '../types/auth';
import { clearSession } from '../utils/authStorage';
import '../index.css';

type Mode = 'INDIVIDUAL' | 'CORPORATE';

export function AdminOnboardingPage() {
  const navigate = useNavigate();
  const [mode, setMode] = useState<Mode>('INDIVIDUAL');
  const [adminSecret, setAdminSecret] = useState('');

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [egn, setEgn] = useState('');

  const [companyName, setCompanyName] = useState('');
  const [eik, setEik] = useState('');
  const [representativeFirstName, setRepresentativeFirstName] = useState('');
  const [representativeLastName, setRepresentativeLastName] = useState('');

  const [email, setEmail] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [result, setResult] = useState<OnboardingResponse | null>(null);

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setIsSubmitting(true);
    setError(null);
    setResult(null);

    try {
      const response =
        mode === 'INDIVIDUAL'
          ? await createIndividualCustomer(adminSecret, {
              firstName,
              lastName,
              egn,
              email,
            })
          : await createCorporateCustomer(adminSecret, {
              companyName,
              eik,
              representativeFirstName,
              representativeLastName,
              email,
            });

      setResult(response);
    } catch (submitError) {
      setError(extractApiErrorMessage(submitError, 'Failed to create customer.'));
    } finally {
      setIsSubmitting(false);
    }
  }

  function handleLogout() {
    clearSession();
    navigate('/login', { replace: true });
  }

  return (
    <div className="home-container">
      <div className="background-shapes">
        <div className="shape shape-1"></div>
        <div className="shape shape-2"></div>
      </div>

      <section className="glass-card auth-card page-card-wide page-content-left">
        <div className="panel-topbar">
          <div className="brand-badge">Employee area</div>
          <button type="button" className="btn-secondary btn-compact" onClick={handleLogout}>
            Logout
          </button>
        </div>

        <h1 className="main-title page-title">Customer onboarding</h1>
        <p className="subtitle page-subtitle page-subtitle-tight">
          Create new bank customers with the secured onboarding endpoints.
        </p>

        <div className="segmented-control-wrap">
          <div className="segmented-control" role="tablist" aria-label="Customer type">
            <button
              type="button"
              className={`segment-btn ${mode === 'INDIVIDUAL' ? 'segment-btn-active' : ''}`}
              onClick={() => setMode('INDIVIDUAL')}
            >
              Individual
            </button>
            <button
              type="button"
              className={`segment-btn ${mode === 'CORPORATE' ? 'segment-btn-active' : ''}`}
              onClick={() => setMode('CORPORATE')}
            >
              Corporate
            </button>
          </div>
        </div>

        <form onSubmit={handleSubmit} className="onboarding-form-grid">
          <label className="form-field">
            <span className="form-label">Admin secret</span>
            <input
              type="password"
              value={adminSecret}
              onChange={(event) => setAdminSecret(event.target.value)}
              required
              autoComplete="off"
              className="glass-input"
            />
          </label>

          <label className="form-field">
            <span className="form-label">Customer email</span>
            <input
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              required
              autoComplete="off"
              className="glass-input"
            />
          </label>

          {mode === 'INDIVIDUAL' ? (
            <>
              <label className="form-field">
                <span className="form-label">First name</span>
                <input
                  type="text"
                  value={firstName}
                  onChange={(event) => setFirstName(event.target.value)}
                  required
                  maxLength={100}
                  className="glass-input"
                />
              </label>

              <label className="form-field">
                <span className="form-label">Last name</span>
                <input
                  type="text"
                  value={lastName}
                  onChange={(event) => setLastName(event.target.value)}
                  required
                  maxLength={100}
                  className="glass-input"
                />
              </label>

              <label className="form-field">
                <span className="form-label">EGN (10 digits)</span>
                <input
                  type="text"
                  value={egn}
                  onChange={(event) => setEgn(event.target.value)}
                  required
                  pattern="[0-9]{10}"
                  maxLength={10}
                  className="glass-input"
                />
              </label>
            </>
          ) : (
            <>
              <label className="form-field">
                <span className="form-label">Company name</span>
                <input
                  type="text"
                  value={companyName}
                  onChange={(event) => setCompanyName(event.target.value)}
                  required
                  maxLength={200}
                  className="glass-input"
                />
              </label>

              <label className="form-field">
                <span className="form-label">EIK (9 to 13 digits)</span>
                <input
                  type="text"
                  value={eik}
                  onChange={(event) => setEik(event.target.value)}
                  required
                  pattern="[0-9]{9,13}"
                  maxLength={13}
                  className="glass-input"
                />
              </label>

              <label className="form-field">
                <span className="form-label">Representative first name</span>
                <input
                  type="text"
                  value={representativeFirstName}
                  onChange={(event) => setRepresentativeFirstName(event.target.value)}
                  required
                  maxLength={100}
                  className="glass-input"
                />
              </label>

              <label className="form-field">
                <span className="form-label">Representative last name</span>
                <input
                  type="text"
                  value={representativeLastName}
                  onChange={(event) => setRepresentativeLastName(event.target.value)}
                  required
                  maxLength={100}
                  className="glass-input"
                />
              </label>
            </>
          )}

          {error && <div className="status-banner status-banner-error">{error}</div>}

          <div className="form-actions">
            <button className="btn-primary submit-btn-fixed" type="submit" disabled={isSubmitting}>
              {isSubmitting ? 'Creating customer...' : 'Create customer'}
            </button>
          </div>
        </form>

        {result && (
          <section className="result-panel" aria-live="polite">
            <h2>Customer created</h2>
            <p>
              <strong>ID:</strong> {result.customerId}
            </p>
            <p>
              <strong>Email:</strong> {result.email}
            </p>
            <p>
              <strong>Type:</strong> {result.customerType}
            </p>
            <p>
              <strong>Temporary password email:</strong>{' '}
              {result.temporaryPasswordSent ? 'Sent successfully' : 'Not sent'}
            </p>
            <p>
              <strong>Delivery channel:</strong> {result.emailDeliveryChannel}
            </p>
            <p>
              <strong>SMTP relay:</strong> {result.emailRelay}
            </p>
            {result.emailDeliveryChannel === 'LOCAL_MAILHOG' ? (
              <p>Email was captured locally. Open http://localhost:8025 to read it.</p>
            ) : (
              <p>Please ask the customer to check inbox and spam folder.</p>
            )}
          </section>
        )}
      </section>
    </div>
  );
}

