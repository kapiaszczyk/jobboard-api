package com.kapia.jobboard.api.data.mapper;

import com.kapia.jobboard.api.data.dto.TechnologyDTO;
import com.kapia.jobboard.api.data.model.Technology;

/**
 * The TechnologyMapper interface provides methods for mapping technology data transfer objects to technology entities.
 */
public interface TechnologyMapper {

    /**
     * Updates a technology entity with the details from a technology data transfer object.
     *
     * @param dto The technology data transfer object.
     * @param technology The technology entity to update.
     * @return The updated technology entity.
     */
    Technology updateTechnologyWithDtoDetails(TechnologyDTO dto, Technology technology);

}
