-- H2 Database Initialization for Shop Service
-- This file will be automatically executed when the application starts

-- Insert sample shops with hardcoded UUIDs
INSERT INTO shops (id, user_id, name, address, description) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'TechTown', '123 Tech Street, Tech City', 'A hub for all things technology.'),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', 'GadgetHub', '456 Gadget Avenue, Gadget City', 'Your one-stop shop for the latest gadgets.'),
('cccccccc-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', 'PeripheralPros', '789 Peripheral Plaza, Peripheral City', 'The best place for computer peripherals.');
