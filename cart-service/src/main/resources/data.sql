-- H2 Database Initialization for Cart Service
-- This file will be automatically executed when the application starts

-- Insert sample cart items
INSERT INTO carts (id, user_id, product_id, product_name, brand, price, quantity) VALUES
('11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '24" Dell FHD Monitor', 'DELL', 300, 5),
('22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Mechanical Logitech Keyboard', 'LOGITECH', 80, 10);
