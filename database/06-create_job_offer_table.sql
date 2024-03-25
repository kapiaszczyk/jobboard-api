CREATE TYPE salary_type_enum AS ENUM ('hourly', 'monthly', 'annual', 'other');

-- Job offer table
CREATE TABLE job_offer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    short_description VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    contract_type VARCHAR(255) NOT NULL,
    salary INT NOT NULL,
    salary_currency VARCHAR(255) NOT NULL,
    salary_type salary_type_enum NOT NULL,
    seniority_level VARCHAR(255) NOT NULL,
    is_remote BOOLEAN NOT NULL,
    is_hybrid BOOLEAN NOT NULL,
    company_id INT,
    FOREIGN KEY (company_id) REFERENCES company(id)
);