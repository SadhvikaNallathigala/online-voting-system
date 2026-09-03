# 🗳️ Online Voting System

A secure and structured **Online Voting System** developed using **Java and Spring Boot**, designed to manage the complete election lifecycle — from election creation and candidate nominations to voter registration, vote casting, vote counting, and result declaration.

The system follows a modular backend architecture with clear separation of responsibilities between controllers, services, repositories, entities, and DTOs.

---            

## 📌 Overview

The Online Voting System provides a digital platform for conducting elections in an organized and reliable manner.

The application manages:

- Election creation and management
- Candidate nominations
- Voter registration and verification
- Candidate viewing
- Vote casting and recording
- Duplicate-vote prevention
- Vote counting and result declaration
- Mandatory recount in case of a tie
- Re-election when a tie remains after recount

The project is designed as a backend-focused application following standard **Spring Boot REST API architecture** and database-driven application development practices.

---

## ✨ Key Features

### 🗳️ Election Management
- Create and manage elections
- Configure election duration
- Track election lifecycle and status
- Prevent invalid or past election dates

### 👤 Voter Management
- Voter registration
- Voter verification
- Controlled participation in elections
- Prevention of duplicate voting

### 👥 Candidate Management
- Candidate nomination
- Candidate registration
- View candidates participating in an election

### ✅ Voting
- Secure vote casting
- Vote recording
- One-vote-per-voter restriction
- Validation before accepting a vote

### 📊 Vote Counting & Results
- Automatic vote counting
- Election result generation
- Winner identification
- Mandatory recount when candidates are tied
- Re-election when the tie remains after recount

### 🔄 Election Lifecycle
The system follows a defined election workflow:

```text
Election Creation
       ↓
Candidate / Nomination
       ↓
Voter Registration
       ↓
Voter Verification
       ↓
Candidate Viewing
       ↓
Vote Casting
       ↓
Vote Recording
       ↓
Vote Counting
       ↓
Result Declaration
       ↓
Tie?
 ┌─────┴─────┐
 No          Yes
 ↓            ↓
Winner     Recount
             ↓
          Still Tie?
         ┌────┴────┐
        No         Yes
        ↓           ↓
     Winner     Re-election
```

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| Spring Boot | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database interaction |
| Hibernate | ORM |
| Maven | Dependency management |
| Lombok | Boilerplate code reduction |
| MySQL | Database |
| REST API | Client-server communication |
| YAML | Application configuration |

---

## 🏗️ Architecture

The application follows a layered architecture:

```text
Client
  │
  ▼
REST Controller
  │
  ▼
Service Layer
  │
  ▼
Repository Layer
  │
  ▼
Database
```

### Controller
Handles HTTP requests and responses.

### Service
Contains the application's business logic and election rules.

### Repository
Handles database operations using Spring Data JPA.

### Entity
Represents persistent database objects.

### DTO
Transfers data between the API and application layers without directly exposing database entities.

---

## 📂 Project Structure

```text
online-voting-system
│
├── src
│   └── main
│       ├── java
│       │   └── com.example.onlinevotingsystem
│       │
│       └── resources
│           └── application.yml
│
├── .gitignore
├── pom.xml
└── README.md
```

> The package structure can be expanded according to the individual election, candidate, voter, voting, and result modules.

---

## 🔐 Important Business Rules

The system follows these core rules:

1. An election must be created before candidates can be added.
2. Candidates must be registered before voting begins.
3. Voters must be registered and verified before casting a vote.
4. A voter can cast only one vote in an election.
5. Duplicate voting is prevented.
6. Votes are recorded only for valid elections and eligible voters.
7. Election results are generated after vote counting.
8. If candidates are tied, a mandatory recount is performed.
9. If the recount still results in a tie, a re-election is conducted.
10. Election dates must be current or future dates.

---

## ⚙️ Prerequisites

Before running the project, make sure the following are installed:

- Java JDK
- Maven
- MySQL
- IntelliJ IDEA / Eclipse / VS Code
- Git

Verify the installations:

```bash
java -version
mvn -version
git --version
```

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/SadhvikaNallathigala/online-voting-system.git
```

### 2. Navigate to the Project

```bash
cd online-voting-system
```

### 3. Configure the Database

Create a MySQL database for the application.

Example:

```sql
CREATE DATABASE online_voting_system;
```

Update the database configuration in:

```text
src/main/resources/application.yml
```

Configure the appropriate:

- Database URL
- Username
- Password
- JPA/Hibernate settings

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The application will start on the configured Spring Boot port.

---

## 🔌 API Design

The application exposes REST APIs for managing the election process.

Typical API modules include:

```text
Election
Candidate / Nomination
Voter
Voting
Results
```

API responses follow a consistent structure:

```json
{
  "success": true,
  "data": {},
  "error": null,
  "meta": {}
}
```

This provides a standardized response format for successful and failed API operations.

---

## 📅 Election Lifecycle

An election progresses through controlled stages:

```text
CREATED
   ↓
NOMINATION
   ↓
VOTER REGISTRATION
   ↓
VOTING
   ↓
COUNTING
   ↓
RESULT DECLARED
```

Election status can be managed based on the configured election duration and lifecycle rules.

---

## 🧪 Testing APIs

The REST APIs can be tested using tools such as:

- Postman
- Swagger UI
- IntelliJ HTTP Client
- cURL

Example:

```bash
curl http://localhost:8080/api/...
```

---

## 📈 Future Enhancements

Possible improvements include:

- JWT-based authentication
- Role-based access control
- Admin dashboard
- Email/SMS verification
- Two-factor authentication
- Election audit logs
- Advanced result analytics
- Docker containerization
- Automated testing with JUnit and Mockito
- CI/CD using GitHub Actions
- Cloud deployment

---

## 🎯 Learning Objectives

This project demonstrates practical implementation of:

- Spring Boot
- REST API development
- Layered architecture
- Spring Data JPA
- Hibernate
- MySQL database integration
- DTO-based API design
- Dependency injection
- Business rule implementation
- Exception handling
- Validation
- Election lifecycle management
- Git and GitHub

---

## 👩‍💻 Author

**Sadhvika Nallathigala**

Computer Science Engineering — Artificial Intelligence

GitHub:  
https://github.com/SadhvikaNallathigala

---

## 📄 License

This project is developed for **educational and learning purposes**.

---

## ⭐ Project

If you find this project useful for learning Spring Boot, REST APIs, or backend application development, consider giving the repository a ⭐ on GitHub.
