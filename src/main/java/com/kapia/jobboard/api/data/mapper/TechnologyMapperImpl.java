package com.kapia.jobboard.api.data.mapper;

import com.kapia.jobboard.api.data.dto.TechnologyDTO;
import com.kapia.jobboard.api.data.model.Technology;
import org.springframework.stereotype.Component;

/**
 * The TechnologyMapperImpl class is an implementation of the TechnologyMapper interface that provides methods for mapping technology data transfer objects to technology entities.
 */
@Component
public class TechnologyMapperImpl implements TechnologyMapper {

    /**
     * Updates a technology entity with the details from a technology data transfer object.
     *
     * @param dto The technology data transfer object.
     * @param technologyToUpdate The technology entity to update.
     * @return The updated technology entity.
     */
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
