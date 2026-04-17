import { useState } from 'react';
import { Link } from 'react-router-dom';
import { createCorporateCustomer, createIndividualCustomer } from '../api/adminOnboarding';
import { extractApiErrorMessage } from '../api/http';
import type { OnboardingResponse } from '../types/auth';

type Mode = 'INDIVIDUAL' | 'CORPORATE';

export function AdminOnboardingPage() {
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

  return (
    <main className="page">
      <section className="card onboarding-card">
        <h1>Secret Admin Onboarding</h1>
        <p>Create bank customers directly with the hidden admin endpoints.</p>

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

        <form onSubmit={handleSubmit} className="form-grid">
          <label>
            Admin secret
            <input
              type="password"
              value={adminSecret}
              onChange={(event) => setAdminSecret(event.target.value)}
              required
              autoComplete="off"
            />
          </label>

          <label>
            Customer email
            <input
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              required
              autoComplete="off"
            />
          </label>

          {mode === 'INDIVIDUAL' ? (
            <>
              <label>
                First name
                <input
                  type="text"
                  value={firstName}
                  onChange={(event) => setFirstName(event.target.value)}
                  required
                  maxLength={100}
                />
              </label>

              <label>
                Last name
                <input
                  type="text"
                  value={lastName}
                  onChange={(event) => setLastName(event.target.value)}
                  required
                  maxLength={100}
                />
              </label>

              <label>
                EGN (10 digits)
                <input
                  type="text"
                  value={egn}
                  onChange={(event) => setEgn(event.target.value)}
                  required
                  pattern="[0-9]{10}"
                  maxLength={10}
                />
              </label>
            </>
          ) : (
            <>
              <label>
                Company name
                <input
                  type="text"
                  value={companyName}
                  onChange={(event) => setCompanyName(event.target.value)}
                  required
                  maxLength={200}
                />
              </label>

              <label>
                EIK (9 to 13 digits)
                <input
                  type="text"
                  value={eik}
                  onChange={(event) => setEik(event.target.value)}
                  required
                  pattern="[0-9]{9,13}"
                  maxLength={13}
                />
              </label>

              <label>
                Representative first name
                <input
                  type="text"
                  value={representativeFirstName}
                  onChange={(event) => setRepresentativeFirstName(event.target.value)}
                  required
                  maxLength={100}
                />
              </label>

              <label>
                Representative last name
                <input
                  type="text"
                  value={representativeLastName}
                  onChange={(event) => setRepresentativeLastName(event.target.value)}
                  required
                  maxLength={100}
                />
              </label>
            </>
          )}

          {error && <p className="error-text">{error}</p>}

          <button className="btn btn-primary" type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'Creating customer...' : 'Create customer'}
          </button>
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

        <Link className="link-muted" to="/">
          Back to home page
        </Link>
      </section>
    </main>
  );
}

