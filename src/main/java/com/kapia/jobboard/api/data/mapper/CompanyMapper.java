package com.kapia.jobboard.api.data.mapper;

import com.kapia.jobboard.api.data.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.data.model.Company;

/**
 * This interface is responsible for mapping company data.
 */
public interface CompanyMapper {

    /**
     * This method is responsible for mapping a company DTO to a company entity.
     *
     * @param dto The DTO containing the company details.
     * @param company The company to update.
     * @return The updated company.
     */
    Company updateCompanyFromDto(CompanyAddressDTO dto, Company company);

}

