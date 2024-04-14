package com.kapia.jobboard.api.controller;

import com.kapia.jobboard.api.model.Technology;
import com.kapia.jobboard.api.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technology")
public class TechnologyController {

    private final TechnologyService technologyService;

    @Autowired
    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @GetMapping
    public Iterable<?> getAllTechnologies() {
        return technologyService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<Technology> technologies) {
        return ResponseEntity.status(HttpStatus.CREATED).body(technologyService.create(technologies));
    }

}
