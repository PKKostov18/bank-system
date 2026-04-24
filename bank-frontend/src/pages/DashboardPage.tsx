import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCustomerAccountStatus, openCustomerAccount } from '../api/account';
import { extractApiErrorMessage } from '../api/http';
import type { AccountStatusResponse } from '../types/auth';
import { clearSession, getSession } from '../utils/authStorage';
import '../index.css';

export function DashboardPage() {
  const navigate = useNavigate();
  const session = getSession();
  const customerType = (session as (Record<string, unknown> | null))?.customerType;
  const roleLabel = session?.role === 'EMPLOYEE'
    ? 'EMPLOYEE'
    : (customerType === 'INDIVIDUAL' || customerType === 'CORPORATE' ? customerType : 'CUSTOMER');
  const [accountStatus, setAccountStatus] = useState<AccountStatusResponse | null>(null);
  const [isLoadingStatus, setIsLoadingStatus] = useState(session?.role === 'CUSTOMER');
  const [hasLoadedStatus, setHasLoadedStatus] = useState(session?.role !== 'CUSTOMER');
  const [isOpeningAccount, setIsOpeningAccount] = useState(false);
  const [accountError, setAccountError] = useState<string | null>(null);
  const [accountSuccess, setAccountSuccess] = useState<string | null>(null);
  const isAccountPanelLoading = !hasLoadedStatus || isLoadingStatus;

  useEffect(() => {
    if (!session || session.role !== 'CUSTOMER') {
      return;
    }

    async function loadAccountStatus() {
      setIsLoadingStatus(true);
      setAccountError(null);

      try {
        const response = await getCustomerAccountStatus();
        setAccountStatus(response);
      } catch (error) {
        setAccountError(extractApiErrorMessage(error, 'Failed to load account status.'));
      } finally {
        setIsLoadingStatus(false);
        setHasLoadedStatus(true);
      }
    }

    void loadAccountStatus();
  }, [session?.customerId, session?.role]);

  function handleLogout() {
    clearSession();
    navigate('/login', { replace: true });
  }

  async function handleOpenAccount() {
    setIsOpeningAccount(true);
    setAccountError(null);
    setAccountSuccess(null);

    try {
      const response = await openCustomerAccount();
      setAccountStatus({
        hasAccount: true,
        accountId: response.accountId,
        iban: response.iban,
        balance: response.balance,
        status: response.status,
      });
      setAccountSuccess(response.message);
    } catch (error) {
      setAccountError(extractApiErrorMessage(error, 'Bank account could not be created.'));
    } finally {
      setIsOpeningAccount(false);
    }
  }

  return (
    <div className="home-container home-container-top">
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
            <strong>Role:</strong> {roleLabel}
          </p>
        </div>

        {session?.role === 'CUSTOMER' && (
          <div className={`result-panel account-status-panel ${isAccountPanelLoading ? 'account-status-panel-loading' : ''}`.trim()}>
            <h2>Your bank account</h2>

            {isAccountPanelLoading ? (
              <div className="account-status-skeleton" aria-label="Loading account status">
                <div className="skeleton-line skeleton-line-short"></div>
                <div className="skeleton-line"></div>
                <div className="skeleton-line skeleton-line-medium"></div>
              </div>
            ) : accountStatus?.hasAccount ? (
              <>
                <p>
                  <strong>IBAN:</strong> {accountStatus.iban}
                </p>
                <p>
                  <strong>Balance:</strong> {accountStatus.balance?.toFixed(2)} BGN
                </p>
                <p>
                  <strong>Status:</strong> {accountStatus.status}
                </p>
              </>
            ) : (
              <>
                <p>You do not have an opened bank account yet.</p>
                <div className="dashboard-actions">
                  <button
                    type="button"
                    className="btn-primary dashboard-open-btn"
                    onClick={handleOpenAccount}
                    disabled={isOpeningAccount}
                  >
                    {isOpeningAccount ? 'Opening account...' : 'Open bank account'}
                  </button>
                </div>
              </>
            )}

            {accountSuccess && <p className="status-success-text">{accountSuccess}</p>}
            {accountError && <p className="status-error-text">{accountError}</p>}
          </div>
        )}
      </section>
    </div>
  );
}

