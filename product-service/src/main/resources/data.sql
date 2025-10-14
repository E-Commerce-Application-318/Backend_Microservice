-- H2 Database Initialization for Product Service
-- This file will be automatically executed when the application starts

-- Insert sample products
-- TechTown products (shop_id: aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa)
INSERT INTO products (id, shop_id, name, description, brand, price, stock_number) VALUES
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '24" Dell FHD Monitor', 'High-quality monitor', 'DELL', 300, 10000),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Mechanical Logitech Keyboard', 'Premium keyboard', 'LOGITECH', 80, 10000),
('ffffffff-ffff-ffff-ffff-ffffffffffff', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Wireless Rapoo Mouse', 'Wireless mouse', 'Rapoo', 50, 10000);

-- GadgetHub products (shop_id: bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb)
INSERT INTO products (id, shop_id, name, description, brand, price, stock_number) VALUES
('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '27" IPS Sony Monitor', 'Professional monitor', 'SONY', 260, 10000),
('22222222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'RGB Logitech Keyboard', 'RGB gaming keyboard', 'LOGITECH', 90, 10000),
('33333333-cccc-cccc-cccc-cccccccccccc', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Ergonomic Anko Mouse', 'Ergonomic mouse', 'ANKO', 40, 10000);

-- PeripheralPros products (shop_id: cccccccc-cccc-cccc-cccc-cccccccccccc)
INSERT INTO products (id, shop_id, name, description, brand, price, stock_number) VALUES
('44444444-dddd-dddd-dddd-dddddddddddd', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'Gaming Headset', 'Professional gaming headset', 'RAZER', 130, 10000),
('55555555-eeee-eeee-eeee-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'Webcam HD', 'High-definition webcam', 'LOGITECH', 70, 10000),
('66666666-ffff-ffff-ffff-ffffffffffff', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'Gaming Mouse Pad', 'Large gaming mouse pad', 'STEELSERIES', 30, 10000);
