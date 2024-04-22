package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.dto.TechnologyDTO;
import com.kapia.jobboard.api.data.exception.ResourceNotFoundException;
import com.kapia.jobboard.api.data.mapper.TechnologyMapper;
import com.kapia.jobboard.api.data.model.Technology;
import com.kapia.jobboard.api.data.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final TechnologyMapper mapper;

    @Autowired
    public TechnologyService(TechnologyRepository technologyRepository, TechnologyMapper mapper) {
        this.technologyRepository = technologyRepository;
        this.mapper = mapper;
    }

    public List<Technology> findAll() {
        return technologyRepository.findAll();
    }

    public List<Technology> create(List<Technology> technologies) {
        return technologyRepository.saveAll(technologies);
    }

    public Technology update(TechnologyDTO dto, long id) {
        Technology technologyToUpdate = technologyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Technology not found"));
        return technologyRepository.save(mapper.updateTechnologyWithDtoDetails(dto, technologyToUpdate));
    }

}
