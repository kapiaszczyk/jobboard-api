package com.kapia.jobboard.api.mapper;

import com.kapia.jobboard.api.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.model.Company;

public interface CompanyMapper {

    Company updateCompanyFromDto(CompanyAddressDTO dto, Company company);

}

