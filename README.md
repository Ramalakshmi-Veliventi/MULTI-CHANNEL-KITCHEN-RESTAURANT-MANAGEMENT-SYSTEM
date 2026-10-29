# Multi-Channel Kitchen & Restaurant Management System

A starter Spring Boot microservices project for a restaurant that manages menu items and orders through a single API gateway.

## Included features
- **Service discovery:** Eureka server.
- **API gateway and load-balanced service routing:** Spring Cloud Gateway routes to `KITCHEN-SERVICE` and `ORDER-SERVICE` through Eureka.
- **Authentication:** demo login endpoint returns a signed HMAC JWT.
- **CRUD operations:** menu items and restaurant orders.
- **Persistence:** H2 in-memory database per service (data resets when that service restarts).
- **Health checks:** Spring Boot Actuator endpoints.

> This is an educational starter project. Demo login credentials and the shared JWT secret are for local demonstration only. Replace them and add proper password storage, authorization rules, validation, and production secrets before real deployment.

## Requirements
- JDK 21
- Maven 3.9+ (Maven 3.10.0 is fine)
- Internet access on the first build so Maven can download dependencies

## Run in this order
Open five Command Prompt windows at the project root and run one command in each:

```cmd
mvn -pl discovery-server spring-boot:run
mvn -pl auth-service spring-boot:run
mvn -pl kitchen-service spring-boot:run
mvn -pl order-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```

Wait for Eureka at http://localhost:8761 and for the services to register before testing the gateway.

## Demo login
```http
POST http://localhost:8080/auth/login
Content-Type: application/json

{"username":"admin","password":"admin123"}
```
Copy the `token` from the response.

## Test menu CRUD (replace TOKEN)
```cmd
curl -X POST http://localhost:8080/kitchen/menu -H "Authorization: Bearer TOKEN" -H "Content-Type: application/json" -d "{\"name\":\"Veg Burger\",\"category\":\"Main Course\",\"price\":120.0,\"available\":true}"
curl http://localhost:8080/kitchen/menu -H "Authorization: Bearer TOKEN"
curl http://localhost:8080/kitchen/menu/1 -H "Authorization: Bearer TOKEN"
curl -X PUT http://localhost:8080/kitchen/menu/1 -H "Authorization: Bearer TOKEN" -H "Content-Type: application/json" -d "{\"name\":\"Paneer Burger\",\"category\":\"Main Course\",\"price\":140.0,\"available\":true}"
curl -X DELETE http://localhost:8080/kitchen/menu/1 -H "Authorization: Bearer TOKEN"
```

## Test order CRUD
```cmd
curl -X POST http://localhost:8080/orders -H "Authorization: Bearer TOKEN" -H "Content-Type: application/json" -d "{\"customerName\":\"Ravi\",\"channel\":\"WEB\",\"itemName\":\"Veg Burger\",\"quantity\":2,\"status\":\"PLACED\"}"
curl http://localhost:8080/orders -H "Authorization: Bearer TOKEN"
```

Supported order channels in the sample: `WEB`, `MOBILE`, `PHONE`, `WALK_IN`, `DELIVERY_PARTNER`.

## Service URLs
- Gateway: http://localhost:8080
- Eureka dashboard: http://localhost:8761
- Auth service direct: http://localhost:8081
- Kitchen service direct: http://localhost:8082
- Order service direct: http://localhost:8083

The gateway's routes use `lb://` service names. To demonstrate load balancing locally, run multiple instances of a service on different ports with the same service name and distinct server ports (for example, configure a second kitchen instance on 8092). For a classroom demo, the included Eureka registration and `lb://` routing demonstrate the load-balancing configuration; verify instance behavior on your machine before claiming a live multi-instance test.

## Important notes
- The PDF supplied with the request did not yield readable text in this environment, so this starter is based on the project title and the microservices/JWT/CRUD/load-balancing requirements discussed earlier.
- This package is source code, not a pre-built executable. Build with `mvn clean package` from this folder.
