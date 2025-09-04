-- Auth Service Database Schema
CREATE DATABASE IF NOT EXISTS auth_db
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;
USE auth_db;
-- Drop tables if they exist
DROP TABLE IF EXISTS users;
-- Create users table for authentication
CREATE TABLE users (
    id binary(16) primary key,
    username varchar(30) not null unique,
    password varchar(255) not null,
    name varchar(100),
    email varchar(100) unique,
    phone_number varchar(20),
    role_type varchar(20) not null,
    birth_date date,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp
);
-- Insert sample users
INSERT INTO users (id, username, password, name, email, phone_number, role_type, birth_date) VALUES
(UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 'admin',      '{bcrypt}$2a$10$adminhash',        'Admin User', 'admin@example.com', '0400000001', 'seller', '1990-01-01'),
(UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 'owner_tech', '{bcrypt}$2a$10$ownerhash1',       'Tech Owner', 'owner.tech@example.com', '0400000002', 'seller', '1985-05-15'),
(UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 'owner_gadg', '{bcrypt}$2a$10$ownerhash2',       'Gadget Owner', 'owner.gadget@example.com', '0400000003', 'seller', '1987-03-22'),
(UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), 'owner_peri', '{bcrypt}$2a$10$ownerhash3',       'Peripheral Owner', 'owner.peripheral@example.com', '0400000004', 'customer', '1989-07-30'),
(UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), 'alice',      '{bcrypt}$2a$10$customerhashalice','Alice Customer', 'alice@example.com', '0400000005', 'customer', '1995-06-18'),
(UUID_TO_BIN('66666666-6666-6666-6666-666666666666'), 'bob',        '{bcrypt}$2a$10$customerhashbob',  'Bob Customer', 'bob@example.com', '0400000006', 'customer', '1993-02-10');

-- Shop Service Database Schema
CREATE DATABASE IF NOT EXISTS shop_db
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;
USE shop_db;
-- Drop tables if they exist
DROP TABLE IF EXISTS shops;
-- Create shops table (Shop Service owns this)
CREATE TABLE shops (
    id binary(16) primary key,
    user_id binary(16) not null,  -- References user from Auth Service
    name varchar(255) not null,
    address text,
    description text,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp
);
-- Insert sample shops
-- Insert sample shops with hardcoded UUIDs
INSERT INTO shops (id, user_id, name, address, description) VALUES
(UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 'TechTown', '123 Tech Street, Tech City', 'A hub for all things technology.'),
(UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 'GadgetHub', '456 Gadget Avenue, Gadget City', 'Your one-stop shop for the latest gadgets.'),
(UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 'PeripheralPros', '789 Peripheral Plaza, Peripheral City', 'The best place for computer peripherals.');

-- Product Service Database Schema
CREATE DATABASE IF NOT EXISTS product_db
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;
USE product_db;
-- Drop tables if they exist
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS shop_products;
-- Create products table (Product Service owns this)
CREATE TABLE products (
    id binary(16) primary key,
    shop_id binary(16),
    name varchar(255) not null,
    description text,
    brand varchar(50),
    price decimal(10,2) not null,
    stock_number int not null default 0
);

-- Insert sample products
-- TechTown
INSERT INTO products (id, shop_id, name, description, brand, price, stock_number) VALUES
(UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '24" Dell FHD Monitor', 'High-quality monitor', 'DELL', 299.99, 25),
(UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), 'Mechanical Logitech Keyboard', 'Premium keyboard', 'LOGITECH', 79.99, 50),
(UUID_TO_BIN('ffffffff-ffff-ffff-ffff-ffffffffffff'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), 'Wireless Rapoo Mouse', 'Wireless mouse', 'Rapoo', 49.99, 80);
-- GadgetHub products (shop_id: bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb)
INSERT INTO products (id, shop_id, name, description, brand, price, stock_number) VALUES
(UUID_TO_BIN('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), '27" IPS Sony Monitor', 'Professional monitor', 'SONY', 259.99, 20),
(UUID_TO_BIN('22222222-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), 'RGB Logitech Keyboard', 'RGB gaming keyboard', 'LOGITECH', 89.99, 40),
(UUID_TO_BIN('33333333-cccc-cccc-cccc-cccccccccccc'), UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), 'Ergonomic Anko Mouse', 'Ergonomic mouse', 'ANKO', 39.99, 60);

-- PeripheralPros products (shop_id: cccccccc-cccc-cccc-cccc-cccccccccccc)
INSERT INTO products (id, shop_id, name, description, brand, price, stock_number) VALUES
(UUID_TO_BIN('44444444-dddd-dddd-dddd-dddddddddddd'), UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), 'Gaming Headset', 'Professional gaming headset', 'RAZER', 129.99, 30),
(UUID_TO_BIN('55555555-eeee-eeee-eeee-eeeeeeeeeeee'), UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), 'Webcam HD', 'High-definition webcam', 'LOGITECH', 69.99, 45),
(UUID_TO_BIN('66666666-ffff-ffff-ffff-ffffffffffff'), UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), 'Gaming Mouse Pad', 'Large gaming mouse pad', 'STEELSERIES', 29.99, 100);

-- Order Service Database Schema
CREATE DATABASE IF NOT EXISTS order_db
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;
USE order_db;
-- Drop tables if they exist
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
-- Create orders table (Order Service owns this)
CREATE TABLE orders (
    id binary(16) primary key,
    user_id binary(16) not null,     -- References user from Auth Service
    status varchar(20) default 'PENDING',  -- PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    total_amount decimal(10, 2) not null,
    payment_status varchar(20) default 'PENDING',  -- PENDING, PAID, FAILED
--     billingAddress text,
    shipping_address text,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp
);
-- Create order items table
CREATE TABLE order_items (
     order_id binary(16) not null,
     product_id binary(16) not null,  -- References product from Product Service
     quantity int not null,
     unit_price decimal(10,2) not null,
     total_price decimal(10,2) not null,
     created_at timestamp default current_timestamp,
     foreign key (order_id) references orders(id) on delete cascade
);
-- Insert sample orders with hardcoded UUIDs
INSERT INTO orders (id, user_id, status, total_amount, payment_status, shipping_address) VALUES
(UUID_TO_BIN('aaaaaaaa-1111-1111-1111-111111111111'), UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), "PENDING",  379.98, 'PENDING', '123 Customer Street, Customer City'),
(UUID_TO_BIN('bbbbbbbb-2222-2222-2222-222222222222'), UUID_TO_BIN('66666666-6666-6666-6666-666666666666'), "PENDING",  299.98, 'CONFIRMED', '456 Customer Avenue, Customer City');
-- Insert sample order items with hardcoded UUIDs
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price) VALUES
-- Order 1 items (Alice's order from TechTown)
(UUID_TO_BIN('aaaaaaaa-1111-1111-1111-111111111111'), UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'), 1, 299.99, 299.99),
(UUID_TO_BIN('aaaaaaaa-1111-1111-1111-111111111111'), UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), 1, 79.99, 79.99),
-- Order 2 items (Bob's order from GadgetHub)
(UUID_TO_BIN('bbbbbbbb-2222-2222-2222-222222222222'), UUID_TO_BIN('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), 1, 259.99, 259.99),
(UUID_TO_BIN('bbbbbbbb-2222-2222-2222-222222222222'), UUID_TO_BIN('33333333-cccc-cccc-cccc-cccccccccccc'), 1, 39.99, 39.99);