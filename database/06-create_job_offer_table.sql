CREATE TYPE salary_type_enum AS ENUM ('hourly', 'monthly', 'annual', 'other');

CREATE CAST (character varying AS salary_type_enum)
  WITH INOUT AS IMPLICIT;

CREATE TABLE job_offer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    short_description VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    contract_type VARCHAR(255) NOT NULL,
    salary INT NOT NULL,
    salary_currency VARCHAR(255) NOT NULL,
    salary_type salary_type_enum NOT NULL,
    experience VARCHAR(255) NOT NULL,
    operating_mode VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    company_id INT,
    address_id INT,
    FOREIGN KEY (company_id) REFERENCES company(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

ALTER TABLE job_offer
ADD CONSTRAINT check_operating_mode
CHECK (operating_mode IN ('remote', 'onsite', 'hybrid'));