package com.kapia.jobboard.api.service;

import com.kapia.jobboard.api.model.Technology;
import com.kapia.jobboard.api.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    @Autowired
    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public List<Technology> findAll() {
        return technologyRepository.findAll();
    }

}
