# Order Service - Quick Reference Guide

## 🚀 Quick Start

### Build & Run
```bash
cd F:\Projects\microservices_v1\order\order
mvn clean install
mvn spring-boot:run
```

### Application URLs
- **REST API:** http://localhost:8082/api/orders
- **Swagger UI:** http://localhost:8082/swagger-ui.html
- **API Docs:** http://localhost:8082/v3/api-docs
- **H2 Console:** http://localhost:8082/h2-console (jdbc:h2:mem:orderdb, sa/empty password)

---

## 📋 API Examples

### 1. Create Order
```bash
POST http://localhost:8082/api/orders
Content-Type: application/json

{
  "orderNumber": "ORD-2026-001",
  "userId": 1,
  "productId": 1,
  "quantity": 5,
  "unitPrice": 19.99,
  "totalPrice": 99.95,
  "status": "PENDING",
  "shippingAddress": "123 Main St, City",
  "createdBy": "admin"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "orderNumber": "ORD-2026-001",
  "userId": 1,
  "productId": 1,
  "quantity": 5,
  "unitPrice": 19.99,
  "totalPrice": 99.95,
  "status": "PENDING",
  "shippingAddress": "123 Main St, City",
  "createdBy": "admin",
  "createdAt": "2026-06-24T20:42:10",
  "version": 0
}
```

### 2. List Orders (Paginated)
```bash
GET http://localhost:8082/api/orders?page=0&size=10&sort=id,desc
```

**Response (200 OK):**
```json
{
  "content": [...orders...],
  "pageable": {...},
  "totalElements": 5,
  "totalPages": 1,
  "size": 10,
  "number": 0,
  "numberOfElements": 5,
  "first": true,
  "last": true,
  "empty": false
}
```

### 3. Get Order by ID
```bash
GET http://localhost:8082/api/orders/1
```

**Response (200 OK):** Order object

**Response (404 Not Found):** If order doesn't exist

### 4. Get Order by Order Number
```bash
GET http://localhost:8082/api/orders/by-number/ORD-2026-001
```

### 5. Update Order
```bash
PUT http://localhost:8082/api/orders/1
Content-Type: application/json

{
  "quantity": 10,
  "totalPrice": 199.90,
  "status": "SHIPPED",
  "updatedBy": "manager"
}
```

**Response (200 OK):** Updated order object

### 6. Delete Order
```bash
DELETE http://localhost:8082/api/orders/1
```

**Response (204 No Content):** Success with no body

---

## 📊 Database Fields

| Field | Type | Length | Nullable | Description |
|-------|------|--------|----------|-------------|
| id | BIGINT | - | NO | Primary Key, Auto-increment |
| order_number | VARCHAR | 50 | NO | Unique order identifier |
| user_id | BIGINT | - | NO | Foreign key to User service |
| product_id | BIGINT | - | NO | Foreign key to Product service |
| quantity | INT | - | NO | Number of items |
| unit_price | DECIMAL | 10,2 | NO | Price per unit |
| total_price | DECIMAL | 10,2 | NO | Total order amount |
| status | VARCHAR | 20 | YES | Order status (PENDING, SHIPPED, etc.) |
| shipping_address | VARCHAR | 500 | YES | Delivery address |
| order_date | TIMESTAMP | - | YES | Order placement date |
| created_by | VARCHAR | 100 | NO | User who created order |
| created_at | TIMESTAMP | - | NO | Creation timestamp (auto) |
| updated_by | VARCHAR | 100 | YES | User who updated order |
| updated_at | TIMESTAMP | - | YES | Update timestamp (auto) |
| version | BIGINT | - | YES | Optimistic lock version |

---

## 🧪 Test Commands

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=OrderServiceImplTest
mvn test -Dtest=OrderControllerTest
```

### Run with Coverage
```bash
mvn test jacoco:report
```

### Test Results
```
Tests run: 13
✅ OrderControllerTest: 3 tests
✅ OrderServiceImplTest: 9 tests
✅ OrderApplicationTests: 1 test
```

---

## 🔧 Configuration

### src/main/resources/application.yaml
```yaml
spring:
  application:
    name: order
  datasource:
    url: jdbc:h2:mem:orderdb
    driverClassName: org.h2.Driver
    username: sa
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    hibernate:
      ddl-auto: create-drop

server:
  port: 8082
```

---

## 📝 Order Status Values

Recommended status values for orders:
- **PENDING** - Order created but not yet confirmed
- **CONFIRMED** - Order has been confirmed
- **PROCESSING** - Order is being processed
- **SHIPPED** - Order has been shipped
- **DELIVERED** - Order has been delivered
- **CANCELLED** - Order has been cancelled
- **RETURNED** - Order has been returned

---

## 🔍 Valid Request Fields

### Create Order (Required fields)
- **orderNumber** (String, max 50) - REQUIRED, UNIQUE
- **userId** (Long) - REQUIRED
- **productId** (Long) - REQUIRED
- **quantity** (Integer, min 1) - REQUIRED
- **unitPrice** (BigDecimal, min 0.01) - REQUIRED
- **totalPrice** (BigDecimal, min 0.01) - REQUIRED
- **createdBy** (String, max 100) - REQUIRED
- **status** (String) - Optional (defaults to "PENDING")
- **shippingAddress** (String, max 500) - Optional
- **orderDate** (LocalDateTime) - Optional

### Update Order (Optional fields)
- All fields except `id`, `createdBy`, `createdAt` can be updated
- Must provide `updatedBy` for audit trail

---

## 🛡️ Validation Rules

| Field | Rules |
|-------|-------|
| orderNumber | Not blank, Max 50 chars, Must be unique |
| userId | Not null |
| productId | Not null |
| quantity | Not null, Minimum 1 |
| unitPrice | Not null, Minimum 0.01 |
| totalPrice | Not null, Minimum 0.01 |
| createdBy | Not blank, Max 100 chars |
| updatedBy | Max 100 chars |
| status | Max 20 chars |
| shippingAddress | Max 500 chars |

---

## 📦 Maven Dependencies Added

```xml
<!-- OpenAPI/Swagger Documentation -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>3.0.2</version>
</dependency>

<!-- Validation Support -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 🐛 Troubleshooting

### H2 Console Connection
- **URL:** `jdbc:h2:mem:orderdb`
- **User:** `sa`
- **Password:** (empty)
- **Driver:** `org.h2.Driver`

### Port Already in Use
Change port in `application.yaml`:
```yaml
server:
  port: 8083  # Change to different port
```

### Database DDL Issues
The schema is auto-created by Spring Boot with `ddl-auto: create-drop`
To persist data between runs, change to `update`:
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

### Test Failures
- Ensure port 8082 is available
- Check Java version (requires Java 21)
- Clear Maven cache: `mvn clean install`

---

## 📚 Project Structure Summary

```
8 Java Source Files
├── 1 Entity (Order)
├── 1 DTO (OrderDto)
├── 1 Repository (OrderRepository)
├── 1 Service Interface (OrderService)
├── 1 Service Implementation (OrderServiceImpl)
├── 1 Mapper (OrderMapper)
├── 1 Controller (OrderController)
└── 1 Application Main (OrderApplication)

3 Test Classes
├── OrderServiceImplTest (9 tests)
├── OrderControllerTest (3 tests)
└── OrderApplicationTests (1 test)

2 Configuration Files
├── pom.xml
└── application.yaml

1 Database Script
└── schema.sql
```

---

## 🚨 Key Implementation Details

### Audit Fields
- **createdBy & createdAt** are set during creation and cannot be modified
- **updatedBy & updatedAt** are updated on every modification
- Uses @CreationTimestamp and @UpdateTimestamp from Hibernate

### Optimistic Locking
- `version` field prevents concurrent modification conflicts
- Automatically managed by JPA/Hibernate

### E-Commerce Integration
- **userId** links to User microservice
- **productId** links to Product microservice
- Allows for distributed system architecture

### No Entity Relationships
- Entity uses foreign key IDs only (userId, productId)
- No @ManyToOne or @OneToMany relationships
- Promotes service independence and resilience

---

**Last Updated:** June 24, 2026
**Version:** 1.0.0
**Status:** Production Ready ✅

