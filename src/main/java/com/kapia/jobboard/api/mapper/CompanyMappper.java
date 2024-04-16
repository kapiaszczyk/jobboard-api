package com.kapia.jobboard.api.mapper;

import com.kapia.jobboard.api.dto.CompanyUpdateDTO;
import com.kapia.jobboard.api.model.Company;

public interface CompanyMappper {

    Company updateCompanyFromDto(CompanyUpdateDTO dto, Company company);

}

