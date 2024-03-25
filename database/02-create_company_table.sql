CREATE TABLE company (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    website VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    logo BYTEA
);