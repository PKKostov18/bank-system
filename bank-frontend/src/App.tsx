import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { ProtectedRoute } from './components/ProtectedRoute';
import { DashboardPage } from './pages/DashboardPage';
import { FirstLoginPage } from './pages/FirstLoginPage';
import { HomePage } from './pages/HomePage';
import { LoginPage } from './pages/LoginPage';
import { AdminOnboardingPage } from './pages/AdminOnboardingPage';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route
                    path="/first-login"
                    element={
                        <ProtectedRoute requireFirstLogin>
                            <FirstLoginPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/app"
                    element={
                        <ProtectedRoute denyFirstLogin>
                            <DashboardPage />
                        </ProtectedRoute>
                    }
                />
                <Route path="/admin/secret-onboarding" element={<AdminOnboardingPage />} />
                <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;