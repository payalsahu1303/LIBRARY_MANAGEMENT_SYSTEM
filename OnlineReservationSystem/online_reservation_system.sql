CREATE DATABASE online_reservation_system;

USE online_reservation_system;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE reservations (
    pnr VARCHAR(20) PRIMARY KEY,
    user_id INT,
    name VARCHAR(100),
    train_number VARCHAR(20),
    train_name VARCHAR(100),
    class_type VARCHAR(20),
    date_of_journey DATE,
    from_location VARCHAR(100),
    to_location VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

INSERT INTO users (login_id, password) VALUES ('user1', 'password1');
INSERT INTO users (login_id, password) VALUES ('user2', 'password2');
INSERT INTO users (login_id, password) VALUES ('user3', 'password3');
INSERT INTO users (login_id, password) VALUES ('user4', 'password4');
INSERT INTO users (login_id, password) VALUES ('user5', 'password5');
SELECT * FROM users;

INSERT INTO reservations (pnr, user_id, name, train_number, train_name, class_type, date_of_journey, from_location, to_location) VALUES
('1234567890', 1, 'Alice Johnson', '101', 'Express Train A', 'Sleeper', '2024-11-01', 'City A', 'City B'),
('1234567891', 2, 'Bob Smith', '102', 'Express Train B', 'AC', '2024-11-02', 'City B', 'City C'),
('1234567892', 3, 'Charlie Brown', '103', 'Express Train C', 'Sleeper', '2024-11-03', 'City C', 'City D'),
('1234567893', 4, 'Diana Prince', '104', 'Express Train D', 'First Class', '2024-11-04', 'City D', 'City E'),
('1234567894', 5, 'Edward Kenway', '105', 'Express Train E', 'Sleeper', '2024-11-05', 'City E', 'City F'),
('1234567895', 1, 'Alice Johnson', '106', 'Express Train F', 'AC', '2024-11-06', 'City F', 'City G'),
('1234567896', 2, 'Bob Smith', '107', 'Express Train G', 'First Class', '2024-11-07', 'City G', 'City H');
SELECT * FROM reservations;
