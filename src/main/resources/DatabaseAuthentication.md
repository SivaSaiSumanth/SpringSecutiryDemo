Postman
↓
username/password
↓
UserDetailsService
↓
Database
↓
users table
↓
PasswordEncoder
↓
Authentication



CREATE TABLE users (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
role VARCHAR(50) NOT NULL
);

CREATE TABLE user_authorities (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
authority VARCHAR(100) NOT NULL,

    CONSTRAINT fk_user_authorities_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

INSERT INTO users (username, password, role)
VALUES
('user', '$2a$10$O03axq9BE6m3Y6/wR2HPAeEfB5eGtT0FWmwN3CjDMy0F2Iay9421G', 'USER'),
('admin', '$2a$10$S7UHhGMBNXjrm6glDIxZpebHOHkaEhZiBhwFeBNO3dHRVSd26wdxC', 'ADMIN'),
('payment', '$2a$10$Uol1QswB6Q8ErWdhk8eI5.xmL19Tge6pKMSpknKutFiWYucbazVzq', 'PAYMENT');

INSERT INTO user_authorities (user_id, authority)
VALUES
(1, 'PAYMENT_READ'),
(2, 'PAYMENT_READ'),
(2, 'PAYMENT_CREATE'),
(2, 'PAYMENT_DELETE'),
(3, 'PAYMENT_CREATE');



Final expected matrix
API	user	admin	payment
/hello	✅ 200	✅ 200	✅ 200
/admin	❌ 403	✅ 200	❌ 403
/payments	✅ 200	✅ 200	❌ 403