package com.kapia.jobboard.api.data.mapper;

import com.kapia.jobboard.api.data.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.data.model.Company;

public interface CompanyMapper {

    Company updateCompanyFromDto(CompanyAddressDTO dto, Company company);

}

