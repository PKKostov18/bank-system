import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { login } from '../api/auth';
import '../index.css';

export function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);

    try {
      await login({ email, password });
      navigate('/dashboard');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Грешен имейл или парола');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="home-container">
      <div className="background-shapes">
        <div className="shape shape-1"></div>
        <div className="shape shape-2"></div>
      </div>

      <div className="glass-card" style={{ maxWidth: '450px' }}>
        <div className="brand-badge">Сигурен достъп</div>

        <h1 className="main-title" style={{ fontSize: '2.5rem', marginBottom: '0.5rem' }}>Вход</h1>
        <p className="subtitle" style={{ marginBottom: '2rem' }}>Добре дошли отново в NBU Bank</p>

        {error && (
          <div style={{
            backgroundColor: 'rgba(239, 68, 68, 0.1)',
            color: '#ef4444',
            padding: '1rem',
            borderRadius: '12px',
            marginBottom: '1.5rem',
            border: '1px solid rgba(239, 68, 68, 0.2)',
            fontSize: '0.9rem'
          }}>
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.2rem' }}>
          <div style={{ textAlign: 'left' }}>
            <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', color: '#94a3b8', marginLeft: '0.5rem' }}>Имейл адрес</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="glass-input"
              placeholder="name@company.com"
            />
          </div>

          <div style={{ textAlign: 'left' }}>
            <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', color: '#94a3b8', marginLeft: '0.5rem' }}>Парола</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="glass-input"
              placeholder="••••••••"
            />
          </div>

            <center>
          <button
            type="submit"
            className="btn-primary"
            disabled={isLoading}
            style={{ marginTop: '1rem', cursor: isLoading ? 'not-allowed' : 'pointer', opacity: isLoading ? 0.7 : 1 }}
          >
            {isLoading ? 'Проверка...' : 'Влез в профила'}
          </button>
          </center>
        </form>

        <div style={{ marginTop: '2rem', fontSize: '0.9rem' }}>
          <Link to="/" style={{ color: '#94a3b8', textDecoration: 'none' }}>← Обратно към началото</Link>
        </div>
      </div>
    </div>
  );
}