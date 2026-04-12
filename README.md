# Curalink API

REST API for managing a medical clinic - patients, doctors, appointments and medical records.

---

## Tech stack

- **Java 21**
- **Spring Boot 3.x**
- **Spring Security + JWT**
- **Spring Data JPA + Hibernate**
- **PostgreSQL**
- **Liquibase** - database migrations
- **Lombok** - boilerplate reduction
- **MapStruct** - entity / DTO mapping
- **JavaFaker** - test data seeding
- **Swagger / OpenAPI** - interactive documentation
- **Docker + Docker Compose** - containerization

---

## Prerequisites

- Java 17+
- Maven
- Docker and Docker Compose

---

## Getting started

### 1. Clone the repository

```bash
git clone https://github.com/Darrylwin/curalink.git
cd curalink
```

### 2. Configure environment variables

```bash
cp .env.example .env
```

Fill in `.env` with your values:

```env
DB_USERNAME=curalink
DB_PASSWORD=your_password
JWT_SECRET=your_base64_256bit_secret_key
JWT_EXPIRATION=86400000
```

To generate a secure JWT key:

```bash
openssl rand -base64 32
```

### 3. Start with Docker

```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080`.

### 4. Start locally (without Docker)

Make sure a PostgreSQL instance is running, then:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## API documentation

Swagger UI is available after startup:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Endpoints

### Authentication

| Method | Route                | Access | Description          |
|--------|----------------------|--------|----------------------|
| POST   | `/api/auth/register` | Public | Patient registration |
| POST   | `/api/auth/login`    | Public | Login                |
| POST   | `/api/auth/refresh`  | Public | Refresh token        |

### Doctors

| Method | Route                               | Access | Description                             |
|--------|-------------------------------------|--------|-----------------------------------------|
| GET    | `/api/doctors`                      | All    | Paginated list, filterable by specialty |
| GET    | `/api/doctors/{id}`                 | All    | Doctor details                          |
| GET    | `/api/doctors/{id}/available-slots` | All    | Available time slots                    |
| POST   | `/api/doctors/{id}/slots`           | ADMIN  | Create a time slot                      |

### Appointments

| Method | Route                             | Access          | Description                  |
|--------|-----------------------------------|-----------------|------------------------------|
| GET    | `/api/appointments`               | ADMIN           | All appointments (paginated) |
| GET    | `/api/appointments/me`            | PATIENT, DOCTOR | My appointments              |
| POST   | `/api/appointments`               | PATIENT         | Book an appointment          |
| PUT    | `/api/appointments/{id}/cancel`   | PATIENT, DOCTOR | Cancel an appointment        |
| PUT    | `/api/appointments/{id}/complete` | DOCTOR          | Mark as completed            |

### Medical records

| Method | Route                               | Access  | Description                    |
|--------|-------------------------------------|---------|--------------------------------|
| GET    | `/api/medical-records/me`           | PATIENT | My medical records             |
| GET    | `/api/medical-records/patient/{id}` | DOCTOR  | Records of a consulted patient |

---

## Authentication

The API uses JWT (JSON Web Token). After login, include the token in every request:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Available roles

| Role      | Description                                                             |
|-----------|-------------------------------------------------------------------------|
| `PATIENT` | Can book appointments and view their own medical records                |
| `DOCTOR`  | Can manage time slots, complete appointments and create medical records |
| `ADMIN`   | Full access to all resources                                            |

---

## Pagination

List endpoints support pagination via query params:

```
GET /api/appointments?page=0&size=10&sort=scheduledAt,asc
GET /api/doctors?specialty=Cardiology&page=0&size=5
```

| Parameter | Default  | Description              |
|-----------|----------|--------------------------|
| `page`    | `0`      | Page number (zero-based) |
| `size`    | `10`     | Number of items per page |
| `sort`    | `id,asc` | Sort field and direction |

---

## Spring profiles

| Profile | Usage             | Database                                  |
|---------|-------------------|-------------------------------------------|
| `dev`   | Local development | `update` - schema updated on each startup |
| `prod`  | Production        | `validate` - Liquibase manages migrations |

```bash
# Start in dev
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Start in prod
java -jar target/curalink.jar
```

---

## Database

### Liquibase migrations

Migrations are located in `src/main/resources/db/changelog/changes/` and follow this convention:

```
V1__create_users_table.sql
V2__create_doctor_profiles_table.sql
V3__create_time_slots_table.sql
V4__create_appointments_table.sql
V5__create_medical_records_table.sql
```

Liquibase runs them automatically at startup in order.

### Data seeding (dev only)

When starting in `dev` profile, the database is automatically populated with:

- 1 administrator
- 5 doctors with profiles and specialties
- 20 patients
- Time slots across 5 working days
- 10 confirmed appointments

Test credentials:

```
Admin   : admin@curalink.com / password
Patient : (check startup logs for generated emails)
Doctor  : (check startup logs for generated emails)
```

---

## Environment variables

| Variable         | Description                       | Example          |
|------------------|-----------------------------------|------------------|
| `DB_USERNAME`    | PostgreSQL user                   | `curalink_user`  |
| `DB_PASSWORD`    | PostgreSQL password               | `supersecret`    |
| `JWT_SECRET`     | JWT secret key (Base64, 256 bits) | `404E635266...`  |
| `JWT_EXPIRATION` | Token lifetime in ms              | `86400000` (24h) |

---

## Docker

### Start

```bash
docker-compose up --build
```

### Useful commands

```bash
docker-compose up --build -d        # start in background
docker-compose logs -f app          # application logs
docker-compose logs -f db           # database logs
docker-compose down                 # stop
docker-compose down -v              # stop and remove volumes
```

### Services

| Service | Port   | Description     |
|---------|--------|-----------------|
| `app`   | `8080` | Spring Boot API |
| `db`    | `5432` | PostgreSQL      |

---

## Tests

Tests have not been written yet.

---

## Logs

In `dev` profile, logs are printed to the console with colorization.
In `prod` profile, logs are written to `logs/app.log` with daily rotation over 30 days.

Format:

```
10:24:31 [http-nio-8080] INFO  c.d.c.s.AppointmentService - Appointment created - id: 42
10:24:31 [http-nio-8080] INFO  c.d.c.RequestLoggingFilter - [127.0.0.1] POST /api/appointments - 201 (87ms)
```