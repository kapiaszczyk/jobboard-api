package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.dto.TechnologyDTO;
import com.kapia.jobboard.api.data.exception.ResourceNotFoundException;
import com.kapia.jobboard.api.data.mapper.TechnologyMapper;
import com.kapia.jobboard.api.data.model.Technology;
import com.kapia.jobboard.api.data.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class represents a service for managing technologies in the job board application.
 */
@Service
public class TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final TechnologyMapper mapper;

    /**
     * Constructs a new TechnologyService with the given technology repository and mapper.
     *
     * @param technologyRepository the technology repository
     * @param mapper the technology mapper
     */
    @Autowired
    public TechnologyService(TechnologyRepository technologyRepository, TechnologyMapper mapper) {
        this.technologyRepository = technologyRepository;
        this.mapper = mapper;
    }

    /**
     * Retrieves all technologies.
     *
     * @return a list of all technologies
     */
    public List<Technology> findAll() {
        return technologyRepository.findAll();
    }

    /**
     * Creates a new technology.
     *
     * @param technologies List of technologies to be created
     * @return the created technologies
     */
    public List<Technology> create(List<Technology> technologies) {
        return technologyRepository.saveAll(technologies);
    }

    /**
     * Updates a technology with the given id.
     *
     * @param id the id of the technology
     * @return the technology with the given id
     */
    public Technology update(TechnologyDTO dto, long id) {
        Technology technologyToUpdate = technologyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Technology not found"));
        return technologyRepository.save(mapper.updateTechnologyWithDtoDetails(dto, technologyToUpdate));
    }

}
