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

- **auth_db** - Users and payment information
- **shop_db** - Shop details and information
- **product_db** - Product catalog and inventory
- **order_db** - Orders and order items
- **cart_db** - Shopping cart items

## Getting Started

### Prerequisites

- Java 21 - Required for Spring Boot 3.3.5
- Maven 3.6+
- Apache Kafka
- IntelliJ IDEA Community Edition or IntelliJ IDEA Ultimate
- Google Gemini API Key from Google AI Studio

### Running Instructions

1. **Clone this project via this repository link:**
https://github.com/E-Commerce-Application-318/Backend_Microservice

2. **Open the project by IntelliJ and refresh Maven by icon “M” on right-side bar in IDEA to install all dependencies**\

3. **Start Kafka:**

**For Windows:**
```bash
# Start Zookeeper (in first terminal)
C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties
```

```bash
# Start Kafka Server (in second terminal)
C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties
```

**For macOS/Linux:**
```bash
# Start Zookeeper (in first terminal)
./kafka/bin/zookeeper-server-start.sh kafka/config/zookeeper.properties
```

```bash
# Start Kafka Server (in second terminal)
./kafka/bin/kafka-server-start.sh kafka/config/server.properties
```
4. **Step 4: Using Google AI Studio to get the API key and set the api-key in the application.yaml located in “agent-service/src/main/resources or you can use my API key already set up (if my current key is invalid please generate new API key)**

5. **Build and run services:**
- **Notice**: Start the “scheduler-service” at the end, after finishing starting all other services

**Method 1:**
- Using StartApplication in each service to start the that service
- Start the scheduler-service the last one, and just start when need to test API from analysis to avoid too many data from auto generation.

**Method 2:**
- Choose “Edit configuration as picture below”
- Add New Configuration and choose SpringBoot (or Application option if using Intellij Community Edition)
- Then browse the file and choose the service
- Tick all the services except the scheduler-service, then right click “Spring Boot” (Or Application) and choose Run. You will see that each service will run on its own port (not including scheduler-service).
- **Start all services except scheduler-service to make the output have better visibility. Run the scheduler-service when need to test API of analytic-serivce.**
- Tick the scheduler-service, then right-click on it; choose Run when you need to test the real-time event streaming

**Method 3: (Optitonal - Not Recommended)**
- Using bash
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
## Sample Input
### Windows Command Prompt Method:
1. Use case 1 (FR1): User Authentication & Registration
- Input (Registration):
```bash
curl -X POST "http://localhost:8081/auth/register" ^
-H "Content-Type: application/json" ^
-d "{\"username\": \"john_doe\", \"password\": \"password123\", \"confirmedPassword\": \"password123\", \"name\": \"John Doe\", \"email\": \"john.doe@email.com\", \"phoneNumber\": \"+1234567890\", \"role\": \"CUSTOMER\", \"birthDate\": \"1990-01-15\"}"
```
- Input (Authentication):
```bash
curl -X GET "http://localhost:8081/auth/login" ^
-H "Content-Type: application/json" ^
-d "{\"username\": \"john_doe\", \"password\": \"password123\"}"
```
2. User case 2 (FR2): Shop Management
- Input (shop info detail):
```bash
curl -X GET "http://localhost:8083/shop/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/shop-detail"
```
- Input (shop info detail and products):
```bash
curl -X GET "http://localhost:8083/shop/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/shop-detail/products"
```

3. Use case 3 (FR3): Product Catalog Management
- Input (Add new product):
```bash
curl -X POST "http://localhost:8082/product/shop/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/add-product" ^
-H "Content-Type: application/json" ^
-d "{\"name\": \"Gaming Monitor 27\\\"\", \"description\": \"High-resolution gaming monitor with 144Hz refresh rate\", \"brand\": \"DELL\", \"price\": 299.99, \"stockNumber\": 15}"
```
- Input (Get all products by pagination):
```bash
curl -X GET "http://localhost:8082/product/all-products?page=1&pagesize=10&sortby=price&sortorder=desc"
```
- Input (Get Product Details by ID):
```bash
curl -X GET "http://localhost:8082/product/product-detail/33333333-cccc-cccc-cccc-cccccccccccc"
```
- Input (Get Products by Shop ID with Pagination):
```bash
curl -X GET "http://localhost:8082/product/shop/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/all-products?page=1&pagesize=5&sortby=name&sortorder=asc"
```

4. Use case 4 (FR4): Shopping Cart Operations - Add product to cart
- Input:
```bash
curl -X POST "http://localhost:8085/cart/11111111-1111-1111-1111-111111111111/add-item" ^
-H "Content-Type: application/json" ^
-d "{\"productId\": \"33333333-cccc-cccc-cccc-cccccccccccc\", \"quantity\": 5}"
```

5. Use case 5 (FR5, FR6): Order Creation & Stock Update
- Input:
```bash
curl -X POST "http://localhost:8084/order/11111111-1111-1111-1111-111111111111/create-order" ^
-H "Content-Type: application/json" ^
-d "[\"11111111-1111-1111-1111-111111111111\", \"22222222-2222-2222-2222-222222222222\"]"
```

6. Use Case 6 (FR5): Order Update
- Input:
```bash
curl -X PUT "http://localhost:8084/order/update-order" ^
-H "Content-Type: application/json" ^
-d "{\"orderId\": \"aaaaaaaa-1111-1111-1111-111111111111\", \"address\": \"Melbourne\", \"phoneNumber\": \"0423111111\"}"
```

7. Use case 7 (FR7): Payment Processing
- Input:
```bash
curl -X PUT "http://localhost:8084/order/payment?orderId=aaaaaaaa-1111-1111-1111-111111111111&userId=11111111-1111-1111-1111-111111111111" ^
-H "Content-Type: application/json" ^
-d "{\"cardNumber\": \"4532758491029384\", \"cardHolderName\": \"VAN BINH NGUYEN\", \"expiryDate\": \"08/2027\", \"cvv\": \"123\"}"
```

Check the order that is changed from “PAYMENT REQUIRED” TO PAID after authorization for payment
- Input:
```bash
curl -X GET http://localhost:8084/order/11111111-1111-1111-1111-111111111111/get-all-orders -H "Content-Type: application/json"
```
8. Use Case 8 (FR8, FR6, FR7): Order Cancellation & Stock Reservation & Refund
- Input
```bash
curl -X PUT "http://localhost:8084/order/cancel-order/aaaaaaaa-1111-1111-1111-111111111111"
```
9. Use case 9 (FR9): AI Customer Support \
- a. Get all orders of the user
- Input:
```bash
curl -G "http://localhost:8086/agents" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=My user ID is 11111111-1111-1111-1111-111111111111. Get all orders."
```
- b. Update an order for the user
- Input:
```bash
curl -G "http://localhost:8086/agents" ^ --data-urlencode "sessionId=1" ^ --data-urlencode "userMessage=Update order aaaaaaaa-1111-1111-1111-111111111111 with new address Town Hall Sydney and new phone number 0423360243"
```
- c. Create order
- Input:
```bash
curl -G "http://localhost:8086/agents" ^ --data-urlencode "sessionId=1" ^ --data-urlencode "userMessage=My userId is 11111111-1111-1111-1111-111111111111 and create order with 1 product of productId dddddddd-dddd-dddd-dddd-dddddddddddd and 2 products of productId 33333333-cccc-cccc-cccc-cccccccccccc"
```
- d. Cancel order
- Step 1: Ask for the cancellation of the order
- Input:
```bash
curl -G "http://localhost:8086/agents" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=My user ID is 11111111-1111-1111-1111-111111111111. Cancel the order has ID bbbbbbbb-2222-2222-2222-222222222222"
```
- Step 2: Confirm cancellation
- Input:
```bash
curl -G "http://localhost:8086/agents" ^ --data-urlencode "sessionId=1" ^ --data-urlencode "userMessage=My user ID is 11111111-1111-1111-1111-111111111111. Yes"
```
- Use case 10 (FR10. FR11): Analysis and event-streaming
- Notice: Run the scheduler-service before testing these APIs
- a. Product analysis
- Input:
```bash
curl -X GET "http://localhost:8087/analytic/product-analytic"
```
- b. Brand analysis
- Input:
```bash
curl -X GET "http://localhost:8087/analytic/brand-analytic"
```
### MacOS/Linux Command Prompt Method:

1. Use case 1 (FR1): User Authentication & Registration
- Input (Registration):
```bash
curl -X POST "http://localhost:8081/auth/register" \
-H "Content-Type: application/json" \
-d "{\"username\": \"john_doe\", \"password\": \"password123\", \"confirmedPassword\": \"password123\", \"name\": \"John Doe\", \"email\": \"john.doe@email.com\", \"phoneNumber\": \"+1234567890\", \"role\": \"CUSTOMER\", \"birthDate\": \"1990-01-15\"}"
```
- Input (Authentication):
```bash
curl -X GET "http://localhost:8081/auth/login" \
-H "Content-Type: application/json" \
-d "{\"username\": \"john_doe\", \"password\": \"password123\"}"
```

2. User case 2 (FR2): Shop Management
- Input (shop info detail):
```bash
curl -X GET "http://localhost:8083/shop/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/shop-detail"
```
- Input (shop info detail and products):
```bash
curl -X GET "http://localhost:8083/shop/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/shop-detail/products"
```

3. Use case 3 (FR3): Product Catalog Management
- Input (Add new product):
```bash
curl -X POST "http://localhost:8082/product/shop/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/add-product" \
-H "Content-Type: application/json" \
-d "{\"name\": \"Gaming Monitor 27\\\"\", \"description\": \"High-resolution gaming monitor with 144Hz refresh rate\", \"brand\": \"DELL\", \"price\": 299.99, \"stockNumber\": 15}"
```
- Input (Get all products by pagination):
```bash
curl -X GET "http://localhost:8082/product/all-products?page=1&pagesize=10&sortby=price&sortorder=desc"
```
- Input (Get Product Details by ID):
```bash
curl -X GET "http://localhost:8082/product/product-detail/33333333-cccc-cccc-cccc-cccccccccccc"
```
- Input (Get Products by Shop ID with Pagination):
```bash
curl -X GET "http://localhost:8082/product/shop/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/all-products?page=1&pagesize=5&sortby=name&sortorder=asc"
```

4. Use case 4 (FR4): Shopping Cart Operations - Add product to cart
- Input:
```bash
curl -X POST "http://localhost:8085/cart/11111111-1111-1111-1111-111111111111/add-item" \
-H "Content-Type: application/json" \
-d "{\"productId\": \"33333333-cccc-cccc-cccc-cccccccccccc\", \"quantity\": 5}"
```

5. Use case 5 (FR5, FR6): Order Creation & Stock Update
- Input:
```bash
curl -X POST "http://localhost:8084/order/11111111-1111-1111-1111-111111111111/create-order" \
-H "Content-Type: application/json" \
-d "[\"11111111-1111-1111-1111-111111111111\", \"22222222-2222-2222-2222-222222222222\"]"
```

6. Use Case 6 (FR5): Order Update
- Input:
```bash
curl -X PUT "http://localhost:8084/order/update-order" \
-H "Content-Type: application/json" \
-d "{\"orderId\": \"aaaaaaaa-1111-1111-1111-111111111111\", \"address\": \"Melbourne\", \"phoneNumber\": \"0423111111\"}"
```

7. Use case 7 (FR7): Payment Processing
- Input:
```bash
curl -X PUT "http://localhost:8084/order/payment?orderId=aaaaaaaa-1111-1111-1111-111111111111&userId=11111111-1111-1111-1111-111111111111" \
-H "Content-Type: application/json" \
-d "{\"cardNumber\": \"4532758491029384\", \"cardHolderName\": \"VAN BINH NGUYEN\", \"expiryDate\": \"08/2027\", \"cvv\": \"123\"}"
```

Check the order that is changed from "PAYMENT REQUIRED" TO PAID after authorization for payment
- Input:
```bash
curl -X GET http://localhost:8084/order/11111111-1111-1111-1111-111111111111/get-all-orders -H "Content-Type: application/json"
```

8. Use Case 8 (FR8, FR6, FR7): Order Cancellation & Stock Reservation & Refund
- Input:
```bash
curl -X PUT "http://localhost:8084/order/cancel-order/aaaaaaaa-1111-1111-1111-111111111111"
```

9. Use case 9 (FR9): AI Customer Support
- a. Get all orders of the user
- Input:
```bash
curl -G "http://localhost:8086/agents" \
--data-urlencode "sessionId=1" \
--data-urlencode "userMessage=My user ID is 11111111-1111-1111-1111-111111111111. Get all orders."
```
- b. Update an order for the user
- Input:
```bash
curl -G "http://localhost:8086/agents" \
--data-urlencode "sessionId=1" \
--data-urlencode "userMessage=Update order aaaaaaaa-1111-1111-1111-111111111111 with new address Town Hall Sydney and new phone number 0423360243"
```
- c. Create order
- Input:
```bash
curl -G "http://localhost:8086/agents" \
--data-urlencode "sessionId=1" \
--data-urlencode "userMessage=My userId is 11111111-1111-1111-1111-111111111111 and create order with 1 product of productId dddddddd-dddd-dddd-dddd-dddddddddddd and 2 products of productId 33333333-cccc-cccc-cccc-cccccccccccc"
```
- d. Cancel order
- Step 1: Ask for the cancellation of the order
- Input:
```bash
curl -G "http://localhost:8086/agents" \
--data-urlencode "sessionId=1" \
--data-urlencode "userMessage=My user ID is 11111111-1111-1111-1111-111111111111. Cancel the order has ID bbbbbbbb-2222-2222-2222-222222222222"
```
- Step 2: Confirm cancellation
- Input:
```bash
curl -G "http://localhost:8086/agents" \
--data-urlencode "sessionId=1" \
--data-urlencode "userMessage=My user ID is 11111111-1111-1111-1111-111111111111. Yes"
```

10. Use case 10 (FR10, FR11): Analysis and event-streaming
- Notice: Run the scheduler-service before testing these APIs
- a. Product analysis
- Input:
```bash
curl -X GET "http://localhost:8087/analytic/product-analytic"
```
- b. Brand analysis
- Input:
```bash
curl -X GET "http://localhost:8087/analytic/brand-analytic"
```

## Service Details

### 1. Auth Service (Port 8081)

**Purpose**: Handles user authentication, registration, and payment processing.

**Database**: `auth_db`

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
| POST | `/auth/login` | User login         | `LoginRequestDTO` |
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

**Database**: `product_db`

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
| POST | `/product/restock-order` | Restock products after order cancellation | `Map<UUID, Integer>` |

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

**Database**: `shop_db`

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

**Database**: `order_db`

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

**Database**: `cart_db`

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
| POST | `/cart/get-product-ids-quantity-by-cart-ids` | Get product IDs and quantities by cart IDs | `List<UUID>` |
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
| GET | `/analytic/product-analytic` | Get product quantity analytics | - |
| GET | `/analytic/brand-analytic` | Get brand revenue analytics | - |

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