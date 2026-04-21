import { useNavigate } from 'react-router-dom';
import { clearSession, getSession } from '../utils/authStorage';
import '../index.css';

export function DashboardPage() {
  const navigate = useNavigate();
  const session = getSession();

  function handleLogout() {
    clearSession();
    navigate('/login', { replace: true });
  }

  return (
    <div className="home-container">
      <div className="background-shapes">
        <div className="shape shape-1"></div>
        <div className="shape shape-3"></div>
      </div>

      <section className="glass-card auth-card page-card-wide page-content-left">
        <div className="panel-topbar">
          <div className="brand-badge">Online banking</div>
          <button type="button" className="btn-secondary btn-compact" onClick={handleLogout}>
            Logout
          </button>
        </div>

        <h1 className="main-title page-title">Dashboard</h1>
        <p className="subtitle page-subtitle page-subtitle-tight">
          You are successfully signed in to your account.
        </p>

        <div className="result-panel">
          <p>
            <strong>Email:</strong> {session?.email}
          </p>
          <p>
            <strong>Role:</strong> {session?.role}
          </p>
        </div>
      </section>
    </div>
  );
}

