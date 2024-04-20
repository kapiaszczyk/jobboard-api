package com.kapia.jobboard.api.controller;

import com.kapia.jobboard.api.dto.TechnologyDTO;
import com.kapia.jobboard.api.model.Technology;
import com.kapia.jobboard.api.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTechnologies() {
        return ResponseEntity.status(HttpStatus.OK).body(technologyService.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody List<Technology> technologies) {
        return ResponseEntity.status(HttpStatus.CREATED).body(technologyService.create(technologies));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TechnologyDTO technologyDTO, @PathVariable(value = "id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(technologyService.update(technologyDTO, id));
    }

}
