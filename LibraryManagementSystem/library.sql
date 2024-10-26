
CREATE DATABASE library;
USE library;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    role ENUM('admin', 'user') DEFAULT 'user'
);

CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100),
    category VARCHAR(50),
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    book_id INT,
    issue_date DATE,
    due_date DATE,
    return_date DATE,
    fine DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

-- Insert Users into the users table
INSERT INTO users (username, password, email, role) VALUES 
('john_doe', 'password123', 'john.doe@example.com', 'user'),
('jane_smith', 'password456', 'jane.smith@example.com', 'user'),
('admin_user', 'adminPass!', 'admin@example.com', 'admin'),
('alice_jones', 'alice2023', 'alice.jones@example.com', 'user'),
('bob_brown', 'bob2023!', 'bob.brown@example.com', 'user'),
('charlie_black', 'charliePass#', 'charlie.black@example.com', 'user'),
('david_williams', 'davidW123', 'david.williams@example.com', 'user'),
('eve_davis', 'evePass!', 'eve.davis@example.com', 'user'),
('frank_martin', 'frank2023$', 'frank.martin@example.com', 'user'),
('grace_lewis', 'grace1234', 'grace.lewis@example.com', 'user');

-- Insert Admin Users into the users table
INSERT INTO users (username, password, email, role) VALUES 
('admin1', 'adminPassword1', 'admin1@example.com', 'admin'),
('admin2', 'adminPassword2', 'admin2@example.com', 'admin');

-- Insert Books into the books table
INSERT INTO books (title, author, category, available) VALUES 
('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', TRUE),
('To Kill a Mockingbird', 'Harper Lee', 'Fiction', TRUE),
('1984', 'George Orwell', 'Dystopian', TRUE),
('Pride and Prejudice', 'Jane Austen', 'Romance', TRUE),
('Moby Dick', 'Herman Melville', 'Adventure', TRUE),
('War and Peace', 'Leo Tolstoy', 'Historical Fiction', TRUE),
('The Odyssey', 'Homer', 'Classic', TRUE),
('Ulysses', 'James Joyce', 'Modernist', TRUE),
('The Catcher in the Rye', 'J.D. Salinger', 'Fiction', TRUE),
('Crime and Punishment', 'Fyodor Dostoevsky', 'Psychological Fiction', TRUE);
