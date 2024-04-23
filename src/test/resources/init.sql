CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT valid_role_name CHECK (name IN ('USER', 'ADMIN', 'SUPER_ADMIN'))
);


INSERT INTO roles (name, description) VALUES ('USER', 'User role');
INSERT INTO roles (name, description) VALUES ('ADMIN', 'Admin role');
INSERT INTO roles (name, description) VALUES ('SUPER_ADMIN', 'Super admin role');