package com.kapia.jobboard.api.mapper;

import com.kapia.jobboard.api.dto.CompanyUpdateDTO;
import com.kapia.jobboard.api.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperImpl implements  CompanyMappper {

    @Override
    public Company updateCompanyFromDto(CompanyUpdateDTO dto, Company companyToUpdate) {

        if (dto == null || companyToUpdate == null) {
            throw new RuntimeException("Company not found");
        }

        Company companyWithNewDetails = dto.getCompany();
        companyWithNewDetails.setId(companyToUpdate.getId());
        companyWithNewDetails.setAddresses(companyToUpdate.getAddresses());

        if (companyWithNewDetails.getName() != null) {
            companyToUpdate.setName(companyWithNewDetails.getName());
        }
        if (companyWithNewDetails.getDescription() != null) {
            companyToUpdate.setDescription(companyWithNewDetails.getDescription());
        }
        if (companyWithNewDetails.getWebsite() != null) {
            companyToUpdate.setWebsite(companyWithNewDetails.getWebsite());
        }
        if (companyWithNewDetails.getEmail() != null) {
            companyToUpdate.setEmail(companyWithNewDetails.getEmail());
        }
        if (companyWithNewDetails.getLogo() != null) {
            companyToUpdate.setLogo(companyWithNewDetails.getLogo());
        }

        return companyWithNewDetails;

    }
}
