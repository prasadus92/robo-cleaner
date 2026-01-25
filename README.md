# Robo Cleaner

A production-grade RESTful web service that simulates a robotic cleaner navigating through a sea area to clean oil spills.

[![CI/CD](https://github.com/prasadus92/robocleaner/actions/workflows/ci.yml/badge.svg)](https://github.com/prasadus92/robocleaner/actions)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Testing](#testing)
- [Deployment](#deployment)
- [Monitoring](#monitoring)

## Overview

The Robo Cleaner service simulates a robotic cleaner that:
- Navigates through a 2D grid representing a sea area
- Cleans oil patches encountered during navigation
- Handles boundary conditions (stays within the defined area)
- Reports the final position and number of cleaned patches

### Coordinate System

```
    Y
    ^
    |
    |  (0,2) (1,2) (2,2)
    |  (0,1) (1,1) (2,1)
    |  (0,0) (1,0) (2,0)
    +-------------------> X
   Origin
```

- **Origin (0,0)**: Bottom-left corner
- **X-axis**: Increases going East
- **Y-axis**: Increases going North
- **Directions**: N (North), S (South), E (East), W (West)

## Features

- **Modern Java 21** with records, pattern matching, and text blocks
- **Spring Boot 3.4** with latest features and optimizations
- **OpenAPI 3.0** documentation with interactive Swagger UI
- **RFC 7807** Problem Details for standardized error responses
- **Comprehensive validation** with custom constraint validators
- **Observability** with Prometheus metrics and health endpoints
- **Docker support** with multi-stage builds and health checks
- **Comprehensive test coverage** with JUnit 5 and REST Assured

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         API Layer                                │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │              NavigationController                        │    │
│  │         POST /api/v1/robocleaner/navigation              │    │
│  └─────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                       Service Layer                              │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │              NavigationService                           │    │
│  │    - Coordinates domain objects                          │    │
│  │    - Handles request/response mapping                    │    │
│  └─────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                       Domain Layer                               │
│  ┌───────────────┐  ┌───────────────┐  ┌──────────────────┐     │
│  │ RoboticCleaner│  │  CleaningArea │  │  CleaningResult  │     │
│  │               │  │               │  │                  │     │
│  │ - Navigation  │  │ - Grid state  │  │ - Final position │     │
│  │ - Cleaning    │  │ - Oil patches │  │ - Patches count  │     │
│  └───────────────┘  └───────────────┘  └──────────────────┘     │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                       Model Layer                                │
│  ┌───────────────┐  ┌───────────────┐  ┌──────────────────┐     │
│  │   Coordinate  │  │   Dimensions  │  │    Direction     │     │
│  │   (record)    │  │   (record)    │  │     (enum)       │     │
│  └───────────────┘  └───────────────┘  └──────────────────┘     │
└─────────────────────────────────────────────────────────────────┘
```

## Technology Stack

| Category | Technology | Version |
|----------|------------|---------|
| Language | Java | 21 (LTS) |
| Framework | Spring Boot | 3.4.1 |
| Build Tool | Maven | 3.9+ |
| API Docs | springdoc-openapi | 2.7.0 |
| Testing | JUnit 5, REST Assured | 5.5.0 |
| Metrics | Micrometer + Prometheus | - |
| Container | Docker | - |

## Getting Started

### Prerequisites

- Java 21 or later
- Maven 3.9+
- Docker (optional)

### Running Locally

```bash
# Clone the repository
git clone https://github.com/prasadus92/robocleaner.git
cd robocleaner

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The service will be available at `http://localhost:8080`

### Running with Docker

```bash
# Build and run with Docker Compose
docker-compose up --build

# Or build manually
docker build -t robo-cleaner .
docker run -p 8080:8080 robo-cleaner
```

## API Documentation

### Interactive Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/api-docs

### Navigation Endpoint

**POST** `/api/v1/robocleaner/navigation`

#### Request

```json
{
  "areaSize": [5, 5],
  "startingPosition": [1, 2],
  "oilPatches": [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "navigationInstructions": "NNESEESWNWW"
}
```

| Field | Type | Description |
|-------|------|-------------|
| `areaSize` | `[width, height]` | Dimensions of the sea area (positive integers) |
| `startingPosition` | `[x, y]` | Initial cleaner position (within bounds) |
| `oilPatches` | `[[x, y], ...]` | List of oil patch coordinates |
| `navigationInstructions` | `string` | Sequence of N, S, E, W directions |

#### Response (200 OK)

```json
{
  "finalPosition": [1, 3],
  "oilPatchesCleaned": 1
}
```

#### Error Response (400 Bad Request)

```json
{
  "type": "https://api.robocleaner.com/errors/validation",
  "title": "Validation Failed",
  "status": 400,
  "detail": "areaSize: Area size must be a pair of positive integers",
  "timestamp": "2024-01-15T10:30:00Z",
  "errorCount": 1
}
```

### Example Usage

```bash
curl -X POST http://localhost:8080/api/v1/robocleaner/navigation \
  -H "Content-Type: application/json" \
  -d '{
    "areaSize": [5, 5],
    "startingPosition": [1, 2],
    "oilPatches": [[1, 0], [2, 2], [2, 3]],
    "navigationInstructions": "NNESEESWNWW"
  }'
```

## Configuration

### Application Properties

| Property | Default | Description |
|----------|---------|-------------|
| `server.port` | 8080 | HTTP port |
| `logging.level.com.marshmallow` | DEBUG | Application log level |
| `management.endpoints.web.exposure.include` | health,info,metrics,prometheus | Exposed actuator endpoints |

### Environment Variables

```bash
# JVM options for production
JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Spring profile
SPRING_PROFILES_ACTIVE=production
```

## Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run with coverage report
mvn verify

# View coverage report
open target/site/jacoco/index.html
```

### Test Structure

```
src/test/java/
├── controller/
│   └── NavigationControllerTest.java    # Integration tests (18 tests)
├── service/
│   └── NavigationServiceTest.java       # Service unit tests
├── domain/
│   └── RoboticCleanerTest.java         # Domain unit tests
└── model/
    └── CoordinateTest.java             # Model unit tests
```

## Deployment

### Docker Image

The Dockerfile uses a multi-stage build for optimal image size:

```bash
# Build image
docker build -t robo-cleaner:latest .

# Run container
docker run -d \
  --name robo-cleaner \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx512m" \
  robo-cleaner:latest
```

### Kubernetes Example

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: robo-cleaner
spec:
  replicas: 3
  selector:
    matchLabels:
      app: robo-cleaner
  template:
    metadata:
      labels:
        app: robo-cleaner
    spec:
      containers:
      - name: robo-cleaner
        image: robo-cleaner:latest
        ports:
        - containerPort: 8080
        livenessProbe:
          httpGet:
            path: /actuator/health/liveness
            port: 8080
          initialDelaySeconds: 10
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
          initialDelaySeconds: 5
        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"
```

## Monitoring

### Health Endpoints

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/info` | Application information |
| `/actuator/metrics` | Application metrics |
| `/actuator/prometheus` | Prometheus metrics |

### Key Metrics

- `navigation.execute.count` - Number of navigation requests
- `navigation.execute.max` - Maximum execution time
- `navigation.execute.mean` - Average execution time

### Prometheus Configuration

```yaml
scrape_configs:
  - job_name: 'robo-cleaner'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
```

## Project Structure

```
robo-cleaner/
├── src/
│   ├── main/
│   │   ├── java/com/marshmallow/robocleaner/
│   │   │   ├── api/                    # Request/Response DTOs
│   │   │   ├── config/                 # Configuration classes
│   │   │   ├── controller/             # REST controllers
│   │   │   ├── domain/                 # Domain entities
│   │   │   ├── exception/              # Custom exceptions
│   │   │   ├── model/                  # Value objects
│   │   │   ├── service/                # Business logic
│   │   │   └── validation/             # Custom validators
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/                       # Test classes
├── .github/
│   └── workflows/
│       └── ci.yml                      # GitHub Actions CI/CD
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## License

This project is licensed under the MIT License.
