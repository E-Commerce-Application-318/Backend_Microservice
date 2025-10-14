# E-Commerce Microservices Backend

A microservices-based e-commerce platform built with Spring Boot, integrated AI-agentic service, implementing Domain-Driven Design (DDD) principles and event-driven architecture using Apache Kafka.

## Architecture Overview

This project follows a microservices architecture with 8 independent services, each with its own database and responsibility:

- **Auth Service** (Port 8081) - User authentication and payment processing
- **Product Service** (Port 8082) - Product catalog management
- **Shop Service** (Port 8083) - Shop information and management
- **Order Service** (Port 8084) - Order processing and management
- **Cart Service** (Port 8085) - Shopping cart functionality
- **Agent Service** (Port 8086) - AI-powered customer support
- **Analytic Service** (Port 8087) - Business analytics and reporting
- **Scheduler Service** - Automated data generation

## Technology Stack

- **Framework**: Spring Boot 3.3.5
- **Language**: Java 21
- **Database**: MySQL 8.0 (separate database per service)
- **Message Broker**: Apache Kafka
- **Architecture Pattern**: Domain-Driven Design (DDD)
- **Communication**: REST APIs + Event-driven messaging
- **AI Integration**: LangChain4j with Google Gemini
- **Build Tool**: Maven
- **Containerization**: Docker

## Database Schema

Each service maintains its own database:

- **auth_db** (Port 3316) - Users and payment information
- **shop_db** (Port 3317) - Shop details and information
- **product_db** (Port 3318) - Product catalog and inventory
- **order_db** (Port 3319) - Orders and order items
- **cart_db** (Port 3320) - Shopping cart items

## Getting Started

### Prerequisites

- Java 21
- Maven 3.6+
- Docker and Docker Compose
- Apache Kafka (running on localhost:9092)

### Environment Setup

1. **Start the databases:**
Create folder ".csit318_environment", if the folder data in ".csit318_environment" already exist so please delete folder "data" befdore run docker compose
```bash
mkdir .csit318_environment 
cd .csit318_environment
docker-compose -f docker-compose-dev.yml up
```

2. **Start Kafka:**
```bash
# Start Zookeeper
C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties

# Start Kafka Server
C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties
```

3. **Build and run services:**
- Using StartApplication in each service to start the that service
```bash
# Build all services
mvn clean install

# Run individual services (in separate terminals)
cd auth-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd shop-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
cd cart-service && mvn spring-boot:run
cd agent-service && mvn spring-boot:run
cd analytic-service && mvn spring-boot:run
cd scheduler-service && mvn spring-boot:run
```

## Service Details

### 1. Auth Service (Port 8081)

**Purpose**: Handles user authentication, registration, and payment processing.

**Database**: `auth_db` (Port 3316)

**Key Features**:
- User registration and login
- Password encryption using BCrypt
- Payment card management
- Payment processing and validation
- Event publishing for order status updates
- Event consumer for payment process and payment refund 

**APIs**:

| Method | Endpoint | Description        | Request Body |
|--------|----------|--------------------|--------------|
| GET | `/auth/index` | Test active endpoint | - |
| GET | `/auth/login` | User login         | `LoginRequestDTO` |
| POST | `/auth/register` | User registration  | `RegisterRequestDTO` |
| GET | `/auth/{userId}/get-user-detail` | Get user details   | - |

**Request/Response Models**:

```json
// LoginRequestDTO
{
  "username": "string",
  "password": "string"
}

// RegisterRequestDTO
{
  "username": "string",
  "password": "string",
  "confirmedPassword": "string",
  "name": "string",
  "email": "string",
  "phoneNumber": "string",
  "role": "string",
  "birthDate": "date"
}

// AuthResponseDTO
{
  "username": "string",
  "role": "string"
}
```

**Event Publishing**:
- `order-status-updated` - Payment status updates

**Event Consumption**:
- `order-payment-processed` - Payment processing events
- `order-cancelled-refund` - Refund events

### 2. Product Service (Port 8082)

**Purpose**: Manages product catalog, inventory, and stock operations.

**Database**: `product_db` (Port 3318)

**Key Features**:
- Product CRUD operations (create, )
- Inventory management
- Stock updates for orders
- Product search and pagination
- Integration with shop service

**APIs**:

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/product/all-products` | Get all products with pagination | Query params: page, pagesize, sortby, sortorder |
| GET | `/product/product-detail/{productId}` | Get product by ID | - |
| POST | `/product/product-detail-list` | Get multiple products by IDs | `List<UUID>` |
| GET | `/product/shop/{shopId}/all-products` | Get products by shop ID | Query params: page, pagesize, sortby, sortorder |
| POST | `/product/shop/{shopId}/add-product` | Add new product to shop | `ProductRequestDTO` |
| POST | `/product/process-order` | Process order and update stock | `Map<UUID, Integer>` |

**Request/Response Models**:

```json
// ProductRequestDTO
{
  "name": "string",
  "description": "string",
  "brand": "string",
  "price": "number",
  "stockNumber": "number"
}

// ProductResponseDTO
{
  "id": "UUID",
  "shopId": "UUID",
  "name": "string",
  "description": "string",
  "brand": "string",
  "price": "number",
  "stockNumber": "number"
}
```

**Event Consumption**:
- `order-created` - Updates product stock when orders are created
- `order-cancelled-restock` - Restores stock when orders are cancelled

### 3. Shop Service (Port 8083)

**Purpose**: Manages shop information and shop-product relationships.

**Database**: `shop_db` (Port 3317)

**Key Features**:
- Shop information management
- Shop-product aggregation
- External API integration with product service

**APIs**:

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/shop/{shopId}/shop-detail` | Get shop details | - |
| GET | `/shop/{shopId}/shop-detail/products` | Get shop details with all products | - |

**Response Models**:

```json
// ShopResponseDTO
{
  "id": "UUID",
  "userId": "UUID",
  "name": "string",
  "address": "string",
  "description": "string",
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}

// ShopProductsResponseDTO
{
  "shopResponse": "ShopResponseDTO",
  "productResponses": "List<ProductResponseDTO>"
}
```

### 4. Order Service (Port 8084)

**Purpose**: Handles order creation, management, and payment processing workflow.

**Database**: `order_db` (Port 3319)

**Key Features**:
- Order creation from cart items
- Update order detail
- Payment processing
- Order cancellation and refunds
- Integration with multiple services

**APIs**:

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/order/{userId}/get-all-orders` | Get all orders for user | - |
| POST | `/order/{userId}/create-order` | Create order from cart items | `List<UUID>` (cartIds) |
| PUT | `/order/update-order` | Update order address/phone | `UpdateOrderRequestDTO` |
| PUT | `/order/payment` | Process payment for order | `PaymentRequestDTO` |
| PUT | `/order/cancel-order/{orderId}` | Cancel order | - |

**Request/Response Models**:

```json
// UpdateOrderRequestDTO
{
  "orderId": "UUID",
  "address": "string",
  "phoneNumber": "string"
}

// PaymentRequestDTO
{
  "cardNumber": "string",
  "cardHolderName": "string",
  "expiryDate": "string",
  "cvv": "string"
}

// OrderResponseDTO
{
  "id": "UUID",
  "userId": "UUID",
  "totalAmount": "number",
  "status": "string",
  "userFullname": "string",
  "shippingAddress": "string",
  "phoneNumber": "string",
  "createdAt": "timestamp",
  "orderItems": "List<OrderItemDTO>"
}
```

**Event Publishing**:

- `order-created` - When new orders are created
- `order-payment-processed` - Payment processing requests
- `order-cancelled-refund` - Refund requests
- `order-cancelled-restock` - Stock restoration requests 

**Event Consumption**:
-  `order-status-updated` - Update status of order when finish payment request  

### 5. Cart Service (Port 8085)

**Purpose**: Manages shopping cart functionality and cart item operations.

**Database**: `cart_db` (Port 3320)

**Key Features**:
- Add/remove items from cart
- Update cart item quantities
- Cart retrieval and management
- Integration with product service for item details

**APIs**:

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/cart/{userId}/get-all-items` | Get all cart items for user | - |
| POST | `/cart/get-carts-by-cart-ids` | Get cart details by cart IDs | `List<UUID>` |
| POST | `/cart/{userId}/add-item` | Add product to cart | `CartRequestDTO` |
| PUT | `/cart/{userId}/update-product` | Update cart item quantity | `CartRequestDTO` |
| DELETE | `/cart/remove-carts` | Remove multiple carts | `List<UUID>` |
| DELETE | `/cart/{userId}/remove-product` | Remove product from cart | `UUID` (productId) |

**Request/Response Models**:

```json
// CartRequestDTO
{
  "productId": "UUID",
  "quantity": "number"
}

// CartResponseDTO
{
  "id": "UUID",
  "userId": "UUID",
  "productId": "UUID",
  "productName": "string",
  "brand": "string",
  "price": "number",
  "quantity": "number"
}

// CartBasketResponseDTO
{
  "cartResponses": "List<CartResponseDTO>",
  "totalAmount": "number"
}
```

**Event Consumption**:
- `order-handled` - Removes cart items when orders are created

### 6. Agent Service (Port 8086)

**Purpose**: Provides AI-powered customer support using LangChain4j and Google Gemini.

**Key Features**:
- Natural language customer support
- Order management
- Order creating and cancelling

**APIs**:

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/agents` | AI customer support chat | Query params: sessionId, userMessage |

**Example Usage**:

```bash
# Get all orders
curl -G "http://localhost:8086/agents" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=Get all orders. My user ID is 11111111-1111-1111-1111-111111111111"

# Update order address
curl -G "http://localhost:8086/agents" \
--data-urlencode "sessionId=1" \
--data-urlencode "userMessage=Update the order. My address is 18 Kenny Street"
```

### 7. Analytic Service (Port 8087)

**Purpose**: Provides analytics and reporting based on order data.

**Key Features**:
- Product quantity analytics
- Order data processing
- Real-time data streaming from Kafka

**APIs**:

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/analytic/quantity-by-product` | Get product quantity analytics | - |

**Response Models**:

```json
// QuantityByProductDTO
{
  "productId": "UUID",
  "productName": "string",
  "totalQuantity": "number"
}
```

**Event Consumption**:
- Processes order events for analytics
- Real-time data aggregation
- Business metrics calculation

### 8. Scheduler Service (Port 8088)

**Purpose**: Automated data generation service.

**Key Features**:
- Automated cart creation
- Automated order generation

**Functionality**:
- Randomly selects users and products
- Creates cart items with random quantities
- Generates orders automatically
- Runs continuously with 3-second intervals

**Integration**:
- Uses Cart Client to add items to cart
- Uses Order Client to create orders
- Simulates real user behavior

## Event-Driven Architecture

The system uses Apache Kafka for event-driven communication between services:

### Event Topics
1. **order-created** - Published when orders are created
2. **order-status-updated** - Payment status updates
3. **order-payment-processed** - Payment processing requests
4. **order-cancelled-refund** - Refund processing
5. **order-cancelled-restock** - Stock restoration

### Event Flow
```
Order Service → [order-created] → Product Service (stock update) & Cart Service (cart removal)
Order Service → [order-payment-processed] → Auth Service (payment)
Auth Service → [order-status-updated] → Order Service (status update)
Order Service → [order-handled] → Cart Service (cleanup)
```

## Shared Domain
The `share-domain` module contains shared event models and DTOs:

- `OrderCreatedEvent` - Order creation events
- `PaymentDetail` - Payment information
- `PaymentStatus` - Payment status updates
- `OrderItemEvent` - Order item details
- `OrderCancelledEventRefund` - Refund events
- `OrderCancelledEventRestock` - Restock events

## 🧪 Testing

### Sample Data

The system includes comprehensive sample data:

**Users**:
- Van Binh (binhng) - Customer
- Trung Hieu (hieung) - Customer
- Nhat Minh (minhng) - Customer
- Dang Bao (baong) - Customer

**Shops**:
- TechTown - Technology products
- GadgetHub - Gadgets and accessories
- PeripheralPros - Computer peripherals

**Products**:
- Monitors, Keyboards, Mice, Headsets, Webcams, Mouse Pads
- Various brands: DELL, LOGITECH, SONY, RAZER, etc.

### Automated Testing

The Scheduler Service provides automated testing by:
- Creating random cart items
- Generating orders automatically
- Simulating user behavior
- Continuous system validation

## Configuration

### Database Configuration

Each service connects to its dedicated MySQL database:

```yaml
# Example: auth-service application.yaml
server:
  port: 8081
spring:
  datasource:
    url: jdbc:mysql://localhost:3316/auth_db
    username: root
    password: root1234
```

### Kafka Configuration

```yaml
spring:
  cloud:
    stream:
      bindings:
        statusUpdatedChannel:
          destination: order-status-updated
      kafka:
        binder:
          brokers: localhost:9092
```

## Monitoring and Analytics

- **Analytic Service** provides business intelligence
- **Real-time event processing** for live analytics
- **Product quantity tracking** and reporting
- **Order analytics** and insights

## API Documentation

All services follow RESTful API conventions with consistent response formats:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... }
}
```

## Contributing

1. Follow DDD principles
2. Maintain service independence
3. Use event-driven communication
4. Write comprehensive tests
5. Document API changes

## License

This project is developed for educational purposes as part of CSIT318 coursework.

---

**Note**: This is a comprehensive microservices e-commerce platform demonstrating modern software architecture patterns, event-driven design, and AI integration in a distributed system.