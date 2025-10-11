-- Auth Service Database Schema
CREATE DATABASE IF NOT EXISTS auth_db
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;
USE auth_db;
-- Drop tables if they exist
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS payments;
-- Create users table for authentication
CREATE TABLE users (
    id binary(16) primary key,
    username varchar(30) not null unique,
    password varchar(255) not null,
    name varchar(100),
    email varchar(100) unique,
    phone_number varchar(20),
    address varchar (255),
    role_type varchar(20) not null,
    birth_date date,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp
);
CREATE TABLE payments
(
    id               binary(16) primary key,
    user_id          binary(16) not null,
    card_number      varchar(20)  not null,
    card_holder_name varchar(100) not null,
    expiry_date      varchar(7)   not null, -- Format: MM/YYYY
    cvv              varchar(4)   not null,
    balance           decimal(10, 2) default 0.00
);
-- Insert sample users
INSERT INTO users (id, username, password, name, email, phone_number, address, role_type, birth_date) VALUES
(UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 'binhng',      '{bcrypt}$2a$10$adminhash',        'Van Binh',     'binh@gmail.com', '0400000001', 'Wollongong', 'customer', '1990-01-01'),
(UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 'hieung', '{bcrypt}$2a$10$ownerhash1',       'Trung Hieu',     'hieu@gmail.com', '0400000002', 'Wollongong', 'customer', '1985-05-15'),
(UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 'minhng', '{bcrypt}$2a$10$ownerhash2',       'Nhat Minh',   'minh@gmail.com', '0400000003','Wollongong', 'customer', '1987-03-22'),
(UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), 'baong', '{bcrypt}$2a$10$ownerhash3',       'Dang Bao', 'bao@gmail.com', '0400000004','Wollongong', 'customer', '1989-07-30');
INSERT INTO payments (id, user_id, card_number, card_holder_name, expiry_date, cvv, balance) VALUES
(UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),UUID_TO_BIN('11111111-1111-1111-1111-111111111111'),'4532758491029384', 'VAN BINH NGUYEN', '08/2027', '123', 100000.00),
(UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),UUID_TO_BIN('22222222-2222-2222-2222-222222222222'),'5500123412345678', 'Trung HIEU NGUYEN', '11/2026', '456', 100000.00),
(UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), '4024007101234567', 'NHAT MINH NGUYEN', '05/2025', '789', 100000.00),
(UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), '6011111111111117', 'DANG BAO NGUYEN', '02/2028', '321', 100000.00);

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
    id binary(16) primary key default(UUID_TO_BIN(UUID())),
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
(UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '24" Dell FHD Monitor', 'High-quality monitor', 'DELL', 300, 10000),
(UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), 'Mechanical Logitech Keyboard', 'Premium keyboard', 'LOGITECH', 80, 10000),
(UUID_TO_BIN('ffffffff-ffff-ffff-ffff-ffffffffffff'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), 'Wireless Rapoo Mouse', 'Wireless mouse', 'Rapoo', 50, 10000);
-- GadgetHub products (shop_id: bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb)
INSERT INTO products (id, shop_id, name, description, brand, price, stock_number) VALUES
(UUID_TO_BIN('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), '27" IPS Sony Monitor', 'Professional monitor', 'SONY', 260, 10000),
(UUID_TO_BIN('22222222-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), 'RGB Logitech Keyboard', 'RGB gaming keyboard', 'LOGITECH', 90, 10000),
(UUID_TO_BIN('33333333-cccc-cccc-cccc-cccccccccccc'), UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), 'Ergonomic Anko Mouse', 'Ergonomic mouse', 'ANKO', 40, 10000);
-- PeripheralPros products (shop_id: cccccccc-cccc-cccc-cccc-cccccccccccc)
INSERT INTO products (id, shop_id, name, description, brand, price, stock_number) VALUES
(UUID_TO_BIN('44444444-dddd-dddd-dddd-dddddddddddd'), UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), 'Gaming Headset', 'Professional gaming headset', 'RAZER', 130, 10000),
(UUID_TO_BIN('55555555-eeee-eeee-eeee-eeeeeeeeeeee'), UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), 'Webcam HD', 'High-definition webcam', 'LOGITECH', 70, 10000),
(UUID_TO_BIN('66666666-ffff-ffff-ffff-ffffffffffff'), UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), 'Gaming Mouse Pad', 'Large gaming mouse pad', 'STEELSERIES', 30, 10000);

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
    id binary(16) primary key default (UUID_TO_BIN(UUID())),
    user_id binary(16) not null,     -- References user from Auth Service
    total_amount decimal(10,2),
    status varchar(20) default 'PAYMENT REQUIRED',  -- PAYMENT REQUIRED, PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    user_fullname varchar(100),
    shipping_address text,
    phone_number varchar(20),
    created_at timestamp default current_timestamp
);
-- Create order items table
CREATE TABLE order_items (
    order_id binary(16) not null,
    product_id binary(16) not null,
    product_name varchar(255) not null,
    brand varchar(50),
    quantity int not null,
    unit_price decimal(10,2) not null,
    total_price decimal(10,2) not null,
    primary key (order_id, product_id)
);
-- Insert sample orders with hardcoded UUIDs
INSERT INTO orders (id, user_id, total_amount, status, user_fullname, shipping_address, phone_number) VALUES
(UUID_TO_BIN('aaaaaaaa-1111-1111-1111-111111111111'), UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 380,  'REQUIRED PAYMENT', 'VAN BINH NGUYEN',  'UOW', '0400000005'),
(UUID_TO_BIN('bbbbbbbb-2222-2222-2222-222222222222'), UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 300, 'REQUIRED PAYMENT',  'VAN BINH NGUYEN', 'Wollongong CBD', '0400000005');

-- Insert sample order items with hardcoded UUIDs
INSERT INTO order_items (order_id, product_id, product_name, brand, quantity, unit_price, total_price) VALUES
-- Order 1 items (Alice's order from TechTown)
(UUID_TO_BIN('aaaaaaaa-1111-1111-1111-111111111111'), UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'), '24" Dell FHD Monitor', 'DELL', 1, 300, 300),
(UUID_TO_BIN('aaaaaaaa-1111-1111-1111-111111111111'), UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), 'Mechanical Logitech Keyboard', 'LOGITECH', 1, 80, 80),
-- Order 2 items (Bob's order from GadgetHub)
(UUID_TO_BIN('bbbbbbbb-2222-2222-2222-222222222222'), UUID_TO_BIN('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '27" IPS Sony Monitor', 'SONY', 1, 260, 260),
(UUID_TO_BIN('bbbbbbbb-2222-2222-2222-222222222222'), UUID_TO_BIN('33333333-cccc-cccc-cccc-cccccccccccc'),'Ergonomic Anko Mouse', 'ANKO',  1, 40, 40);

-- Cart Service Database Schema
CREATE DATABASE IF NOT EXISTS cart_db
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;
USE cart_db;
-- Drop tables if they exist
DROP TABLE IF EXISTS carts;
CREATE TABLE carts (
    id binary(16) primary key default (UUID_TO_BIN(UUID())),
    user_id binary(16) not null,
    product_id binary(16) not null,
    product_name varchar(255) not null,
    brand varchar(50),
    price decimal(10,2) not null,
    quantity int not null
);

-- insert into carts (id, user_id, total_item, total_amount) values
insert into carts(user_id, product_id, product_name, brand, price, quantity) values
(UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'),'24" Dell FHD Monitor', 'DELL', 300, 5),
(UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), 'Mechanical Logitech Keyboard', 'LOGITECH', 80, 10);
-- (UUID_TO_BIN('aaaaaaaa-1111-1111-1111-111111111111'), UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), 0, 0.00),  -- Alice's cart
-- (UUID_TO_BIN('bbbbbbbb-2222-2222-2222-222222222222'), UUID_TO_BIN('66666666-6666-6666-6666-666666666666'), 0, 0.00),  -- Bob's cart
-- (UUID_TO_BIN('cccccccc-3333-3333-3333-333333333333'), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), 0, 0.00),  -- Peripheral Owner's cart
-- (UUID_TO_BIN('dddddddd-4444-4444-4444-444444444444'), UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 0, 0.00),  -- Gadget Owner's cart
-- (UUID_TO_BIN('eeeeeeee-5555-5555-5555-555555555555'), UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 0, 0.00),  -- Tech Owner's cart
