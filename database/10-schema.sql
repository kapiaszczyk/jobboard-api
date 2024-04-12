-- CREATE TABLE technology (
--     id SERIAL PRIMARY KEY,
--     name VARCHAR(255) NOT NULL,
--     description TEXT NOT NULL,
--     logo BYTEA
-- );

-- CREATE TABLE company (
--     id SERIAL PRIMARY KEY,
--     name VARCHAR(255) NOT NULL,
--     description TEXT NOT NULL,
--     website VARCHAR(255) NOT NULL,
--     email VARCHAR(255) NOT NULL,
--     logo BYTEA
-- );

-- CREATE TABLE address (
--     id SERIAL PRIMARY KEY,
--     street VARCHAR(255) NOT NULL,
--     city VARCHAR(255) NOT NULL,
--     postal_code VARCHAR(255) NOT NULL,
--     country VARCHAR(255) NOT NULL,
--     company_id INT NOT NULL REFERENCES company(id),
--     FOREIGN KEY (company_id) REFERENCES company(id)
-- );

-- CREATE TYPE salary_type_enum AS ENUM ('hourly', 'monthly', 'annual', 'other');

-- CREATE TYPE operating_mode_enum AS ENUM ('remote', 'hybrid', 'in-office');

-- -- Job offer table
-- CREATE TABLE job_offer (
--     id SERIAL PRIMARY KEY,
--     name VARCHAR(255) NOT NULL,
--     short_description VARCHAR(255) NOT NULL,
--     description TEXT NOT NULL,
--     contract_type VARCHAR(255) NOT NULL,
--     salary INT NOT NULL,
--     salary_currency VARCHAR(255) NOT NULL,
--     salary_type salary_type_enum NOT NULL,
--     experience VARCHAR(255) NOT NULL,
--     operating_mode operating_mode_enum NOT NULL,
--     company_id INT,
--     address_id INT,
--     FOREIGN KEY (company_id) REFERENCES company(id)
--     FOREIGN KEY (address_id) REFERENCES address(id)
-- );

-- -- Job offer technology table
-- CREATE TABLE job_offer_technology (
--     job_offer_id INT NOT NULL REFERENCES job_offer(id),
--     technology_id INT NOT NULL REFERENCES technology(id),
--     degree_of_knowledge VARCHAR(255) NOT NULL,
--     PRIMARY KEY (job_offer_id, technology_id),
--     FOREIGN KEY (job_offer_id) REFERENCES job_offer(id),
--     FOREIGN KEY (technology_id) REFERENCES technology(id)
-- );
