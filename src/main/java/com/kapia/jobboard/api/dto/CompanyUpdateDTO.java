package com.kapia.jobboard.api.dto;

import com.kapia.jobboard.api.model.Company;

public class CompanyUpdateDTO {

    Company company;

    public CompanyUpdateDTO() {
    }

    public Company getCompany() {
    return company;
    }

    public void setCompany(Company company) {
    this.company = company;
    }

}
