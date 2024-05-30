-- Create database if not exists
CREATE DATABASE IF NOT EXISTS alkem6 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use database
USE alkem6;

-- Insert records into the users table
INSERT INTO users (name, username, email, pass, balance)
VALUES
    ('John Doe', 'john_doe', 'john@example.com', 'password123', 1000.00),
    ('Alice Smith', 'alice_smith', 'alice@example.com', 'securepass', 2500.00),
    ('Bob Johnson', 'bob_johnson', 'bob@example.com', 'password123', 1500.00),
    ('Charlie Brown', 'charlie_brown', 'charlie@example.com', 'mypassword', 1200.00);

-- Insert records into the contacts table
INSERT INTO contacts (username, user_id, email)
VALUES
    ('user1_contact1', 1, 'alice@example.com'),
    ('user2_contact1', 2, 'bob@example.com'),
    ('user3_contact1', 3, 'john@example.com'),
    ('user1_contact2', 1, 'bob@example.com'),
    ('user2_contact2', 2, 'charlie@example.com'),
    ('user3_contact2', 3, 'alice@example.com'),
    ('user4_contact1', 4, 'john@example.com');

-- Additional transactions for user 1 (John Doe)
INSERT INTO transactions (message, type, email_sender, email_receiver, total, sender_id)
VALUES
    ('Bill payment', 'WITHDRAW', 'john@example.com', 'john@example.com', 120.00, 1),
    ('Online purchase', 'WITHDRAW', 'john@example.com', 'john@example.com', 80.00, 1),
    ('Phone recharge', 'WITHDRAW', 'john@example.com', 'john@example.com', 30.00, 1),
    ('ATM withdrawal', 'WITHDRAW', 'john@example.com', 'john@example.com', 50.00, 1);

-- Additional transactions for user 2 (Alice Smith)
INSERT INTO transactions (message, type, email_sender, email_receiver, total, sender_id)
VALUES
    ('Rebate deposit', 'DEPOSIT', 'alice@example.com', 'alice@example.com', 200.00, 2),
    ('Rent payment', 'WITHDRAW', 'alice@example.com', 'alice@example.com', 400.00, 2),
    ('Supermarket purchase', 'WITHDRAW', 'alice@example.com', 'alice@example.com', 150.00, 2),
    ('Loan payment', 'WITHDRAW', 'alice@example.com', 'alice@example.com', 100.00, 2);

-- Additional transactions for user 3 (Bob Johnson)
INSERT INTO transactions (message, type, email_sender, email_receiver, total, sender_id)
VALUES
    ('Ticket purchase', 'WITHDRAW', 'bob@example.com', 'bob@example.com', 120.00, 3),
    ('Bonus deposit', 'DEPOSIT', 'bob@example.com', 'bob@example.com', 50.00, 3),
    ('Membership payment', 'WITHDRAW', 'bob@example.com', 'bob@example.com', 80.00, 3),
    ('Subscription payment', 'WITHDRAW', 'bob@example.com', 'bob@example.com', 30.00, 3);

-- Additional transactions for user 4 (Charlie Brown)
INSERT INTO transactions (message, type, email_sender, email_receiver, total, sender_id)
VALUES
    ('Water bill payment', 'WITHDRAW', 'charlie@example.com', 'charlie@example.com', 50.00, 4),
    ('Electronics purchase', 'WITHDRAW', 'charlie@example.com', 'charlie@example.com', 300.00, 4),
    ('Savings deposit', 'DEPOSIT', 'charlie@example.com', 'charlie@example.com', 100.00, 4),
    ('Pet store purchase', 'WITHDRAW', 'charlie@example.com', 'charlie@example.com', 70.00, 4);
