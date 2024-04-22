package com.kapia.jobboard.api.data.mapper;

import com.kapia.jobboard.api.data.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.model.Company;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public Company updateCompanyFromDto(@NotNull CompanyAddressDTO dto, @NotNull Company companyToUpdate) {

        Company companyFromDto = dto.getCompany();
        String name = getValueOrDefault(companyFromDto.getName(), companyToUpdate.getName());
        String description = getValueOrDefault(companyFromDto.getDescription(), companyToUpdate.getDescription());
        String website = getValueOrDefault(companyFromDto.getWebsite(), companyToUpdate.getWebsite());
        String email = getValueOrDefault(companyFromDto.getEmail(), companyToUpdate.getEmail());
        byte[] logo = getValueOrDefault(companyFromDto.getLogo(), companyToUpdate.getLogo());

        Set<Address> addresses = new HashSet<>();
        if (dto.getAddresses() != null) {
            addresses.addAll(dto.getAddresses());
        }
        addresses.addAll(companyToUpdate.getAddresses());

        Company companyWithNewDetails = new Company();
        companyWithNewDetails.setId(companyToUpdate.getId());
        companyWithNewDetails.setName(name);
        companyWithNewDetails.setDescription(description);
        companyWithNewDetails.setWebsite(website);
        companyWithNewDetails.setEmail(email);
        companyWithNewDetails.setLogo(logo);
        companyWithNewDetails.addAddresses(addresses);

        return companyWithNewDetails;
    }

    private <T> T getValueOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
