# 💳 Payment Processing Service

A Spring Boot application that processes payments for shared children (e.g., Student 1) and ensures accurate balance updates for both Parent A and Parent B.

---

## 🚀 Features

- Process payments for children shared between parents
- Automatically updates balances for multiple guardians
- RESTful API design
- Dockerized for portability and deployment

---

## 📦 Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- H2 (in-memory database for dev/testing)
- Docker
- Maven

---

## 🛠️ Getting Started

### Prerequisites

- [Java 21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)

---

### 🚧 Run Locally (without Docker)

```bash
# Clone the repository
git clone https://github.com/your-username/payment_processing.git
cd payment_processing

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
