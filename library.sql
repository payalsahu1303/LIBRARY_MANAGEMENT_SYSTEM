
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

INSERT INTO users (username, password, email, role) VALUES 
('admin1', 'adminPassword1', 'admin1@example.com', 'admin'),
('admin2', 'adminPassword2', 'admin2@example.com', 'admin');

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

INSERT INTO transactions (user_id, book_id, issue_date, due_date, return_date, fine) VALUES
(1, 1, '2024-10-01', '2024-10-15', '2024-10-14', 0.00),
(2, 2, '2024-10-02', '2024-10-16', '2024-10-17', 5.00),
(3, 3, '2024-10-03', '2024-10-17', NULL, 0.00),  
(4, 4, '2024-10-04', '2024-10-18', '2024-10-18', 0.00),
(5, 5, '2024-10-05', '2024-10-19', '2024-10-21', 10.00),
(6, 6, '2024-10-06', '2024-10-20', NULL, 0.00),  
(7, 7, '2024-10-07', '2024-10-21', '2024-10-20', 0.00),
(8, 8, '2024-10-08', '2024-10-22', '2024-10-24', 15.00),
(9, 9, '2024-10-09', '2024-10-23', NULL, 0.00),  
(10, 10, '2024-10-10', '2024-10-24', '2024-10-23', 0.00);

SELECT * FROM users;

SELECT * FROM books;

SELECT * FROM transactions;
