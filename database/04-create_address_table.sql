CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    company_id INT NOT NULL REFERENCES company(id),
    FOREIGN KEY (company_id) REFERENCES company(id)
);