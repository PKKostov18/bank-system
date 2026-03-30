# Bank Management System

A full-stack web application designed to manage bank clients, bank accounts, and credit services. This project implements a robust monolithic Spring Boot backend and a modern React with TypeScript frontend, utilizing Docker for seamless database management.

## 🚀 Features

### Client & Account Management
* **Register Clients:** Support for both Physical Persons (Name, EGN) and Legal Entities (Company Name, EIK, Representative).
* **Open Bank Accounts:** Generate and assign IBANs to clients, track account balances, and manage account statuses (Active/Closed).
* **Relationships:** A single client can hold multiple bank accounts and multiple credits.

### Credit Services & Installment Plans
* **Grant Credits:** Issue Consumer and Mortgage credits with specific interest rates, maximum amounts, and duration limits.
* **Annuity Installment Plans:** Automatically generate a month-by-month repayment schedule using the annuity formula.
* **Payment Tracking:** Mark specific monthly installments as paid.
* **Credit Status:** Real-time monitoring of remaining balances and overall credit status (Active/Paid).

---

## 🛠️ Tech Stack

**Backend**
* **Framework:** Spring Boot 3.x (Java 21)
* **Build Tool:** Gradle (Groovy)
* **Database Access:** Spring Data JPA / Hibernate
* **Validation:** Spring Boot Starter Validation
* **Utilities:** Lombok, Spring Boot DevTools

**Frontend**
* **Library:** React 18
* **Language:** TypeScript
* **Build Tool:** Vite
* **Routing:** React Router DOM
* **HTTP Client:** Axios

**Infrastructure & Database**
* **Database:** MySQL 8.0
* **Containerization:** Docker & Docker Compose

---

## 📂 Project Structure

The repository is structured as a monorepo containing three main directories:

```text
bank-system-project/
├── bank-backend/           # Spring Boot REST API (Java)
├── bank-frontend/          # React + TypeScript UI
└── docker/                 # Infrastructure configuration
    └── docker-compose.yml
