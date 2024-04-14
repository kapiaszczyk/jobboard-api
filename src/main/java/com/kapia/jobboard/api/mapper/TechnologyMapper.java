package com.kapia.jobboard.api.mapper;

import com.kapia.jobboard.api.dto.TechnologyDTO;
import com.kapia.jobboard.api.model.Technology;

public interface TechnologyMapper {

    Technology updateTechnologyWithDtoDetails(TechnologyDTO dto, Technology technology);

}
