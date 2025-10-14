-- H2 Database Initialization for Order Service
-- This file will be automatically executed when the application starts

-- Insert sample orders with hardcoded UUIDs
INSERT INTO orders (id, user_id, total_amount, status, user_fullname, shipping_address, phone_number, created_at) VALUES
('aaaaaaaa-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 380.00, 'REQUIRED PAYMENT', 'VAN BINH NGUYEN', 'UOW', '0400000005', CURRENT_TIMESTAMP),
('bbbbbbbb-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111', 300.00, 'REQUIRED PAYMENT', 'VAN BINH NGUYEN', 'Wollongong CBD', '0400000005', CURRENT_TIMESTAMP);

-- Insert sample order items with hardcoded UUIDs
INSERT INTO order_items (order_id, product_id, product_name, brand, quantity, unit_price, total_price) VALUES
-- Order 1 items (Van Binh's order from TechTown)
('aaaaaaaa-1111-1111-1111-111111111111', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '24" Dell FHD Monitor', 'DELL', 1, 300.00, 300.00),
('aaaaaaaa-1111-1111-1111-111111111111', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Mechanical Logitech Keyboard', 'LOGITECH', 1, 80.00, 80.00),
-- Order 2 items (Van Binh's order from GadgetHub)
('bbbbbbbb-2222-2222-2222-222222222222', '11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '27" IPS Sony Monitor', 'SONY', 1, 260.00, 260.00),
('bbbbbbbb-2222-2222-2222-222222222222', '33333333-cccc-cccc-cccc-cccccccccccc', 'Ergonomic Anko Mouse', 'ANKO', 1, 40.00, 40.00);
