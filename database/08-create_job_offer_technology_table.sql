-- Job offer technology table
CREATE TABLE job_offer_technology (
    job_offer_id INT NOT NULL REFERENCES job_offer(id),
    technology_id INT NOT NULL REFERENCES technology(id),
    degree_of_knowledge VARCHAR(255) NOT NULL,
    PRIMARY KEY (job_offer_id, technology_id),
    FOREIGN KEY (job_offer_id) REFERENCES job_offer(id),
    FOREIGN KEY (technology_id) REFERENCES technology(id)
);
