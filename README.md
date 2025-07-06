# Taskify: Full-Stack To-Do Application

## Project Overview

This repository contains a simple full-stack to-do application built with:

* **React** (frontend SPA)
* **Spring Boot** (Java REST API)
* **MySQL** (relational database)
* **Docker & Docker Compose** for containerization

Users can:

1. Create tasks with a title and description.
2. View the 5 most recent incomplete tasks.
3. Mark tasks as complete (they disappear from the list).

## Table of Contents

* [Prerequisites](#prerequisites)
* [Project Structure](#project-structure)
* [Getting Started (Docker)](#getting-started-docker)
* [Inspecting the Database](#inspecting-the-database)
* [Running Without Docker](#running-without-docker)
* [Running Tests](#running-tests)

  * [Backend Unit & Integration Tests](#backend-unit--integration-tests)
  * [Frontend Unit Tests](#frontend-unit-tests)
  * [End-to-End (E2E) Tests](#end-to-end-e2e-tests)
* [Environment Variables](#environment-variables)
* [CI Integration](#ci-integration)
* [License](#license)

---

## Prerequisites

* Docker Desktop (macOS/Windows) or Docker Engine & Docker Compose (Linux)
* Java 17 (for local backend runs)
* Node.js 16+ & npm or Yarn (for local frontend runs)
* Chrome browser & ChromeDriver (for Selenium E2E tests)

---

## Project Structure

```
todo-fullstack/            # root for docker-compose
├── backend/               # Spring Boot app
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── frontend/              # React app
│   ├── src/
│   ├── package.json
│   └── Dockerfile
└── docker-compose.yml     # orchestrates db, backend, frontend
```

---

## Getting Started (Docker)

1. **Build & start all services**

   ```bash
   docker-compose up --build
   ```

2. **Access the app**

   * Frontend UI: [http://localhost:3000](http://localhost:3000)
   * Backend API: [http://localhost:8080/api/tasks](http://localhost:8080/api/tasks)

3. **Stop & remove**

   ```bash
   docker-compose down
   ```

---

## Inspecting the Database

To view the `task` table contents:

```bash
# Open a shell into the MySQL container
docker-compose exec db mysql -u user -ppass todo

# At the MySQL prompt:
SHOW TABLES;
SELECT * FROM task ORDER BY created_at DESC LIMIT 10;
EXIT;
```

Alternatively, expose port 3306 in `docker-compose.yml` and connect with your favorite GUI (e.g. MySQL Workbench).

---

## Running Without Docker

### Backend

```bash
cd backend
# configure application.properties if needed
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install      # or yarn install
npm start        # or yarn start
```

---

## Running Tests

### Backend Unit & Integration Tests

```bash
cd backend
# Run JUnit tests (unit, repository slice, controller IT)
mvn test
```

### Frontend Unit Tests

```bash
cd frontend
npm install --save-dev @testing-library/react @testing-library/jest-dom
npm test       # runs Jest + React Testing Library
```

### End-to-End (E2E) Tests (Selenium)

1. Ensure the app is running (Docker or manual).
2. Install ChromeDriver:

   ```bash
   brew install --cask chromedriver
   ```
3. Run the Selenium test:

   ```bash
   cd backend
   mvn test -Dtest=com.CoverageX.Take.Home.Assessment.e2e.TodoE2ETest
   ```

---

## Environment Variables

| Name                         | Default                     | Description                  |
| ---------------------------- | --------------------------- | ---------------------------- |
| `REACT_APP_API_URL`          | `http://localhost:8080`     | Base URL for frontend axios  |
| `SPRING_DATASOURCE_URL`      | `jdbc:mysql://db:3306/todo` | JDBC URL for MySQL container |
| `SPRING_DATASOURCE_USERNAME` | `user`                      | MySQL user                   |
| `SPRING_DATASOURCE_PASSWORD` | `pass`                      | MySQL password               |

---

## CI Integration

* Use **GitHub Actions** or another CI to:

  1. Build & start Docker Compose (`docker-compose up -d --build ui, docker-compose up -d --build`).
  2. Run backend tests (`mvn test`).
  3. Run frontend tests (`npm test -- --coverage`).
  4. Run E2E tests via Selenium or Playwright.

---

## License

This project is open-source and available under the MIT License.
