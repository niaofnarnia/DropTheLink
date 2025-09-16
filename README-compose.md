# 🎶 DropTheLink: Docker Compose Guide

This document provides instructions for running the DropTheLink application using Docker Compose in different environments.

---

## 📌 Prerequisites
Docker and Docker Compose installed

Java 21 (for local development)

MySQL 8.0+ (for local development)

---
## ⚙️ Environment Files
Create a .env file in the project root with the following variables:

### Database Configuration
````
DB_USERNAME=root
DB_PASSWORD=yourpassword
MYSQL_DATABASE=drop_the_link_db
````
---
## JWT Configuration
````
JWT_SECRET_KEY=c3VwZXJzZWNyZXRqd3RrZXkxMjM0NTY3ODkwYWJjZGVmZ2hpams=
JWT_EXPIRATION=1800000
````
---
## 🚀 Available Docker Compose Configurations
1. Production Environment
   File: docker-compose.yml

Description: Main production setup with MySQL and Spring Boot application.

### Start the application
````
docker-compose up -d
````
### View logs
````
docker-compose logs -f
````
### Stop the application
````
docker-compose down
````

### Stop and remove volumes (caution: deletes data)
````
docker-compose down -v
````
---
## Services
mysql-db: MySQL 8.0.43 database on port 3307

spring-app: Spring Boot application on port 8080

2. Testing Environment (Basic)
   File: docker-compose-test.yml

Description: Testing setup that runs tests using a pre-built image.

### Run tests
````
docker-compose -f docker-compose-test.yml up --abort-on-container-exit
````
### Clean up
````
docker-compose -f docker-compose-test.yml down -v
````

## Services
mysql-test-db: MySQL test database on port 3308

spring-test-app: Runs Maven tests and exits

3. Testing Environment (With Custom Dockerfile)
   File: docker-compose-test-with-dockerfile.yml

Description: Testing setup that builds a custom testing image using Dockerfile-test.

### Build and run tests
````
docker-compose -f docker-compose-test-with-dockerfile.yml up --build --abort-on-container-exit
````
### Clean up
````
docker-compose -f docker-compose-test-with-dockerfile.yml down -v
````
Services
mysql-test-db: MySQL test database on port 3308

spring-test-app: Custom-built test container with Maven and MySQL client

### 🗺️ Port Mapping
Service

Internal Port

External Port

Purpose

Production MySQL

3306

3307

Main database

Test MySQL

3306

3308

Test database

Spring App (Prod)

8080

8080

Main application

Spring App (Test)

8080

8081

Test application

---
### 📄 Dockerfile Explanations
Dockerfile
Production-ready image for the Spring Boot application

Uses OpenJDK 21 slim

Copies the compiled JAR and runs it

Dockerfile-test
Testing-focused image with Maven and MySQL client

Includes test execution scripts

Waits for database readiness before running tests

Includes health checks

## 💻 Common Commands
Development
### Build the application locally
````
./mvnw clean package
````
### Run with local profile
````
./mvnw spring-boot:run -Dspring.profiles.active=local
````
Docker Operations
### View running containers
````
docker ps
````
### View all containers (including stopped)
````
docker ps -a
````
### View logs for specific service
````
docker-compose logs mysql-db
docker-compose logs spring-app
````
### Execute commands in running container
````
docker-compose exec spring-app bash
docker-compose exec mysql-db mysql -u root -p
````
Database Operations
### Connect to production database
````
docker-compose exec mysql-db mysql -u root -p drop_the_link_db
````
### Connect to test database
````
docker-compose -f docker-compose-test.yml exec mysql-test-db mysql -u root -p drop_the_link_db_test
````
### Backup database
````
docker-compose exec mysql-db mysqldump -u root -p drop_the_link_db > backup.sql
````
### Restore database
````
docker-compose exec -T mysql-db mysql -u root -p drop_the_link_db < backup.sql
````
⚠️ Troubleshooting
Common Issues
Port Already in Use

### Check what's using the port
````
lsof -i :3307
lsof -i :8080
````
### Kill the process or change port in docker-compose.yml

Permission Denied on mvnw

chmod +x mvnw

MySQL Connection Issues

Ensure MySQL container is healthy: docker-compose ps

Check logs: docker-compose logs mysql-db

Verify environment variables are set correctly

Out of Memory Errors

# Increase Docker memory limit or add to docker-compose.yml:
environment:
- MAVEN_OPTS=-Xmx1024m

Useful Docker Commands
### Remove all stopped containers
````
docker container prune
````
### Remove unused images
````
docker image prune
````
### Remove unused volumes
````
docker volume prune
````
### Complete cleanup (caution: removes everything)
````
docker system prune -a --volumes
````
## ⚙️ CI/CD Integration
This setup integrates with GitHub Actions workflows:

build.yml: Builds the application

test.yml: Runs tests with MySQL service

release.yml: Builds and pushes Docker images to DockerHub

Make sure to set the following secrets in your GitHub repository:

DOCKER_USERNAME: Your DockerHub username

DOCKER_PASSWORD: Your DockerHub password/token

🔗 API Endpoints
Once running, the application exposes these endpoints:

Authentication
POST /api/auth/register - Register new user

POST /api/auth/login - User login

Playlists
GET /api/playlists/my - Get user's playlists

GET /api/playlists/public - Get public playlists

POST /api/playlists - Create playlist

PUT /api/playlists/{id} - Update playlist

DELETE /api/playlists/{id} - Delete playlist

Videos
POST /api/videos - Add video to playlist

DELETE /api/videos/playlist/{playlistId}/video/{videoId} - Remove video

ℹ️ Environment-Specific Notes
Production (docker-compose.yml)
Uses persistent volumes for database

Configured for stability and performance

Database accessible on port 3307

Testing (docker-compose-test*.yml)
Isolated test environment

Temporary databases that are cleaned up after tests

Test database on port 3308

Runs tests and exits