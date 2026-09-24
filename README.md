FitnessGoals is a full-stack fitness tracking web application built with Spring Boot and React. 
Track your daily meals, lifting exercises, cardio sessions, and water intake.

---
## Prerequisites

Make sure you have the following installed before proceeding:

- [Java 17+](https://adoptium.net)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Node.js 18+ and npm](https://nodejs.org)
- [PostgreSQL 15+](https://www.postgresql.org/download/)
- [pgAdmin 4](https://www.pgadmin.org/download/) (optional but recommended)

---

## Database Setup

1. Open pgAdmin 4 or the PostgreSQL shell
2. Create a new database:
```sql
CREATE DATABASE fitnessgoals;
```

---

## Backend Setup

1. Clone the repository:
```bash
git clone https://github.com/your-username/fitnessgoals.git
cd fitnessgoals
```

2. Navigate to the backend folder:
```bash
cd backend
```

3. Open `src/main/resources/application.properties` and update the database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fitnessgoals
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
```

4. Also set your JWT secret in `application.properties`:
```properties
jwt.secret=your-secret-key-must-be-at-least-32-characters-long
jwt.expiration=86400000
```

5. Run the backend:
```bash
mvn spring-boot:run
```

6. The API will be available at `http://localhost:8080`

Spring Boot will automatically create all the required database tables on startup.

---

## Frontend Setup

1. Open a new terminal and navigate to the frontend folder:
```bash
cd frontend
```

2. Create a `.env` file in the `frontend` folder:

3. Install dependencies:
```bash
npm install
```

4. Start the frontend:
```bash
npm start
```

5. The app will be available at `http://localhost:3000`

---

## Running the App

Once both the backend and frontend are running:

1. Open `http://localhost:3000` in your browser
2. Click **Register** to create an account
3. Log in with your credentials
4. Start tracking your fitness goals!
