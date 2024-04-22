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

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role_id INT,
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO users (username, email, password, role_id) VALUES ('superadmin', 'main@jobboard.com', '$2a$12$CgulUhi2Rc9lXxF8Y/Qj..9sFr02lgk6qtQjlm5hg0rsktqf4ZX2q', 3);

