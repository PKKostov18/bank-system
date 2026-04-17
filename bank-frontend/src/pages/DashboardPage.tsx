import { useNavigate } from 'react-router-dom';
import { clearSession, getSession } from '../utils/authStorage';

export function DashboardPage() {
  const navigate = useNavigate();
  const session = getSession();

  function handleLogout() {
    clearSession();
    navigate('/login', { replace: true });
  }

  return (
    <main className="page">
      <header className="topbar">
        <h1>Online Banking</h1>
        <button className="btn btn-secondary" onClick={handleLogout}>
          Sign out
        </button>
      </header>

      <section className="card">
        <h2>Welcome</h2>
        <p>You have successfully signed in to the system.</p>
        <p>Email: {session?.email}</p>
        <p>Role: {session?.role}</p>
      </section>
    </main>
  );
}

