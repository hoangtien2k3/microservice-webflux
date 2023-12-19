# Microservice-Webflux
Microservice-webflux with spring boot, reactor-core, Liquibase database migration, docker, apache kafka.

## Prerequisites
Before you begin, ensure you have met the following requirements:

- Java Development Kit `(JDK) 17` or higher installed.
- Build tool (e.g., `Maven`) installed.
- Database system (e.g., `MySQL`, `Liquibase`) set up and configured.
- Kafka sends message
- Liquibase db migration
- Database R2DBC
- Spring Cloud API-GATEWAY
- Skima Validate `Json`
- Docker and docker-compose

## Features
- ✅ Using `Microservices` as a high-level architecture

## Getting Started
Follow these steps to set up and run the backend:

1. Clone the repository:
```bash
   git clone https://github.com/hoangtien2k3qx1/microservice-webflux.git
```

#### 1. Navigate to the project directory:
```bash
  cd project-name-backend
```

#### 2. Build the project:
```bash
  # Using Maven
  mvn clean install
  
  # Using Gradle
  gradle build
```

#### 3. Configure the database:
- Update `application.properties` or `application.yml` with your database connection details.


#### 4. Run the application:
```bash
  # Using Maven
  mvn spring-boot:run
  
  # Using Gradle
  gradle bootRun
```

## Technologies Used
- `Java`: The primary programming language.
- `Spring Boot`: Framework for building Java-based enterprise applications.
- `Maven/Gradle`: Build tools for managing dependencies and building the project.
- `Database`: Choose and specify the database system used (e.g., MySQL, PostgreSQL).
- `Other Dependencies`: List any additional dependencies or libraries used.

## API Documentation
Document the API endpoints and their functionalities. You can use tools like `Swagger` for automated `API documentation`.

## Contributing
If you would like to contribute to the development of this project, please follow our contribution guidelines.

## License
This project is licensed under the [`MIT License`](LICENSE).
