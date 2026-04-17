import { Link } from 'react-router-dom';

export function HomePage() {
  return (
    <main className="page page-center">
      <section className="card hero-card">
        <h1>Bank System</h1>
        <p>Secure online banking for individual and corporate customers.</p>
        <p>Access is available only for existing customers created through a bank branch.</p>
        <div className="hero-actions">
          <Link className="btn btn-primary" to="/login">
            Sign in
          </Link>
          <Link className="btn btn-secondary" to="/admin/secret-onboarding">
            Open secret onboarding form
          </Link>
        </div>
      </section>
    </main>
  );
}
