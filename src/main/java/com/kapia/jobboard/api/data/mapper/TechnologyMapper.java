package com.kapia.jobboard.api.data.mapper;

import com.kapia.jobboard.api.data.dto.TechnologyDTO;
import com.kapia.jobboard.api.data.model.Technology;

public interface TechnologyMapper {

    Technology updateTechnologyWithDtoDetails(TechnologyDTO dto, Technology technology);

}
