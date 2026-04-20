import React from 'react';
import { Link } from 'react-router-dom';
import '../index.css';

export function HomePage() {
  return (
    <div className="home-container">
      <div className="background-shapes">
        <div className="shape shape-1"></div>
        <div className="shape shape-2"></div>
        <div className="shape shape-3"></div>
      </div>

      <div className="glass-card">
        <div className="brand-badge">
          NBU Bank System
        </div>

        <h1 className="main-title">
          Вашите пари, <br/>
          <span className="gradient-text">преосмислени</span>.
        </h1>

        <p className="subtitle">
          Вашият сигурен и надежден партньор в банковото дело. Управлявайте сметките си с лекота и сигурност.
        </p>

        <div className="action-buttons">
          <Link to="/login" className="btn-primary">
            Вход
          </Link>

          <Link to="/admin/onboarding/secret-path" className="btn-secondary">
            Admin Onboarding
          </Link>
        </div>
      </div>

      <footer className="footer">
        © 2024 NBU Bank System. Всички права запазени.
      </footer>
    </div>
  );
}