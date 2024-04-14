package com.kapia.jobboard.api.mapper;

import com.kapia.jobboard.api.dto.TechnologyDTO;
import com.kapia.jobboard.api.model.Company;
import com.kapia.jobboard.api.model.Technology;
import org.springframework.stereotype.Component;

@Component
public class TechnologyMapperImpl implements TechnologyMapper {

    @Override
    public Technology updateTechnologyWithDtoDetails(TechnologyDTO dto, Technology technologyToUpdate) {

        if (dto == null || technologyToUpdate == null) {
            throw new RuntimeException("Company not found");
        }

        if (dto.getName() != null) {
            technologyToUpdate.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            technologyToUpdate.setDescription(dto.getDescription());
        }
        if (dto.getLogo() != null) {
            technologyToUpdate.setLogo(dto.getLogo());
        }
        return technologyToUpdate;
    }

}
