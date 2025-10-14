-- H2 Database Initialization for Auth Service
-- This file will be automatically executed when the application starts

-- Insert sample users
INSERT INTO users (id, username, password, name, email, phone_number, address, role_type, birth_date) VALUES
('11111111-1111-1111-1111-111111111111', 'binhng', '{bcrypt}$2a$10$adminhash', 'Van Binh', 'binh@gmail.com', '0400000001', 'Wollongong', 'customer', '1990-01-01'),
('22222222-2222-2222-2222-222222222222', 'hieung', '{bcrypt}$2a$10$ownerhash1', 'Trung Hieu', 'hieu@gmail.com', '0400000002', 'Wollongong', 'customer', '1985-05-15'),
('33333333-3333-3333-3333-333333333333', 'minhng', '{bcrypt}$2a$10$ownerhash2', 'Nhat Minh', 'minh@gmail.com', '0400000003', 'Wollongong', 'customer', '1987-03-22'),
('44444444-4444-4444-4444-444444444444', 'baong', '{bcrypt}$2a$10$ownerhash3', 'Dang Bao', 'bao@gmail.com', '0400000004', 'Wollongong', 'customer', '1989-07-30');

-- Insert sample payment cards
INSERT INTO payments (id, user_id, card_number, card_holder_name, expiry_date, cvv, balance) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', '4532758491029384', 'VAN BINH NGUYEN', '08/2027', '123', 100000.00),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', '5500123412345678', 'Trung HIEU NGUYEN', '11/2026', '456', 100000.00),
('cccccccc-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', '4024007101234567', 'NHAT MINH NGUYEN', '05/2025', '789', 100000.00),
('dddddddd-dddd-dddd-dddd-dddddddddddd', '44444444-4444-4444-4444-444444444444', '6011111111111117', 'DANG BAO NGUYEN', '02/2028', '321', 100000.00);
