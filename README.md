# Restaurant Service

Microservice developed in Java with Spring Boot to manage restaurants, menus, and seat availability. This service is part of a microservices architecture for restaurant reservations, where each component has its own responsibility and communicates with the others through REST APIs and Kafka events.

## Overview

The system was designed as a distributed application composed of several microservices:

- **Restaurant Service**: manages restaurants, menu items, and availability slots.
- **Reservation Service**: manages customer reservations and updates seat availability.
- **Notification Service**: sends notifications related to reservations, cancellations, or availability changes.

This separation allows each service to evolve independently, keep a clear responsibility, and scale according to its own load.

## Restaurant Service

This repository contains the **Restaurant Service**. The main goal of this microservice is to centralize restaurant information and expose the data that other services need to create reservations, check menus, or analyze capacity.

Main features:

- Create, list, retrieve, update, and delete restaurants.
- Manage menu items associated with each restaurant.
- Create and query availability slots by restaurant, date, and time.
- Reserve and release seats in an availability slot.
- Publish Kafka events when seat availability increases.

## Project architecture

The code is organized into layers:

- `controller`: exposes the REST endpoints.
- `service`: contains business logic and coordinates repositories and events.
- `repository`: handles data access with Spring Data JPA.
- `model`: JPA entities persisted in the database.
- `dto`: objects used in requests, responses, and event messages.
- `mapper`: converts between entities and DTOs.
- `events`: Kafka producers.
- `exception`: specific exceptions and global error handling.

## Technologies used

- Java 24
- Spring Boot 3.4.4
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Kafka
- Spring Actuator
- PostgreSQL
- Flyway
- Docker
- Docker Compose
- Maven
- Lombok
- OpenAPI / Swagger UI
- pgAdmin

## Database

The service uses PostgreSQL as its main database. Database migrations are managed with Flyway and are stored in the `migrations` folder.

Main tables:

- `restaurants`: stores restaurant data.
- `menu_items`: stores menu items associated with each restaurant.
- `availability_slots`: stores time slots, capacity, and available seats.

Main relationships:

- One restaurant can have multiple menu items.
- One restaurant can have multiple availability slots.
- Each menu item and each slot belongs to one restaurant.

## System microservices

### Restaurant Service

Responsible for restaurant information. It manages data such as name, city, country, phone, email, menus, and availability.

It also provides basic metrics through the metrics endpoint, such as:

- Total available seats.
- Number of availability slots.

### Reservation Service

Responsible for creating and managing reservations. In a normal flow, this service checks availability through the Restaurant Service and then calls endpoints to reserve or release seats.

Example responsibilities:

- Create a reservation.
- Cancel a reservation.
- Confirm availability.
- Update available seats in the Restaurant Service.
- Publish reservation-related events, if needed.

### Notification Service

Responsible for sending notifications to users. It can react to Kafka events or direct calls from other services.

Example notifications:

- Reservation confirmation.
- Reservation cancellation.
- Time slot change.
- New seat availability.

This service is separated so that sending emails, SMS, or other messages does not block the main reservation logic.

### Analytics Service

Responsible for collecting data and generating metrics about the system.

It can consume Kafka events to analyze:

- Number of reservations per restaurant.
- Most requested time slots.
- Availability changes.
- Occupancy rate.
- Restaurants with the highest demand.

Keeping analytics in its own microservice avoids mixing statistical processing with the operational logic of restaurants and reservations.

## Main endpoints

### Restaurants

Base path:

```text
/api/restaurants
```

Endpoints:

- `POST /api/restaurants`: creates a restaurant.
- `GET /api/restaurants`: lists all restaurants.
- `GET /api/restaurants/{restaurantId}`: retrieves a restaurant by id.
- `PUT /api/restaurants/{restaurantId}`: updates a restaurant.
- `DELETE /api/restaurants/{restaurantId}`: deletes a restaurant.

### Menu

Base path:

```text
/api/restaurants/{restaurantId}/menu-items
```

Endpoints:

- `POST /api/restaurants/{restaurantId}/menu-items`: creates a menu item.
- `GET /api/restaurants/{restaurantId}/menu-items`: lists the restaurant menu items.
- `PUT /api/restaurants/{restaurantId}/menu-items/{menuItemId}`: updates a menu item.
- `DELETE /api/restaurants/{restaurantId}/menu-items/{menuItemId}`: deletes a menu item.

### Availability

Base path:

```text
/api/restaurants/{restaurantId}/availability
```

Endpoints:

- `POST /api/restaurants/{restaurantId}/availability`: creates an availability slot.
- `GET /api/restaurants/{restaurantId}/availability`: lists availability slots.
- `GET /api/restaurants/{restaurantId}/availability/{slotId}`: retrieves a specific slot.
- `PUT /api/restaurants/{restaurantId}/availability/{slotId}`: updates a slot.
- `DELETE /api/restaurants/{restaurantId}/availability/{slotId}`: deletes a slot.
- `POST /api/restaurants/{restaurantId}/availability/reserve`: reserves seats in a slot.
- `POST /api/restaurants/{restaurantId}/availability/release`: releases seats in a slot.

Supported filters when listing availability:

- `date`: filters by date.
- `time`: filters by a time inside the slot interval.
- `partySize`: filters slots with enough available seats.

Example:

```text
GET /api/restaurants/{restaurantId}/availability?date=2026-07-11&time=20:00&partySize=4
```

## Configuration

The main variables are in the `.env` file:

```env
PORT=8091
APPLICATION_NAME=restaurant-service

POSTGRES_DB=restaurant_db
POSTGRES_USER=restaurant_user
POSTGRES_PASSWORD=restaurant_secure_password_2025

PGADMIN_DEFAULT_EMAIL=admin@example.com
PGADMIN_DEFAULT_PASSWORD=admin
```

The application configuration is in `src/main/resources/application.yml`.

Important points:

- Port configured through an environment variable.
- PostgreSQL connection through JDBC.
- Kafka configured with `kafka:9092`.
- JSON serialization for Kafka messages.
- Actuator exposed for `health`, `info`.
- Swagger UI available at `/swagger-ui.html`.

## Run with Docker Compose

Before starting, the external network used by Compose must exist:

```bash
docker network create restaurant-network
```

Then:

```bash
docker compose up --build
```

Services defined in `compose.yml`:

- `restaurant_db`: PostgreSQL database.
- `flyway`: runs the SQL migrations.
- `restaurant-service`: Spring Boot application.
- `pgadmin`: graphical interface to manage the database.

Main ports:

- Restaurant Service: `8091`
- pgAdmin: `7777`

Note: Kafka must be available on hostname `kafka` and port `9092`, usually through the same Docker network used by the other microservices.

## Run locally with Maven

With PostgreSQL and Kafka available:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
./mvnw.cmd spring-boot:run
```

To compile and run tests:

```bash
./mvnw test
```

## API documentation

With the application running:

```text
http://localhost:8091/swagger-ui.html
```

Health check:

```text
http://localhost:8091/actuator/health
```

## Example flow

A common system flow can be:

1. The Restaurant Service creates a restaurant.
2. Menu items are added to that restaurant.
3. Availability slots are created with capacity and available seats.
4. The Reservation Service checks availability.
5. When a reservation is made, the Reservation Service reduces the available seats through the Restaurant Service.
6. If a reservation is cancelled or capacity increases, seats are released or updated.
7. The Notification Service can consume the event and notify users.

## Applied best practices

- Separation of responsibilities by layer.
- DTOs to avoid exposing database entities directly.
- Input data validation.
- Centralized exception handling.
- Versioned migrations with Flyway.
- Asynchronous communication with Kafka.
- Reproducible environment with Docker Compose.
- Monitoring endpoints with Spring Actuator.
- Interactive documentation with Swagger UI.