package com.kapia.jobboard.api.data.controller;

import com.kapia.jobboard.api.data.dto.TechnologyDTO;
import com.kapia.jobboard.api.data.model.Technology;
import com.kapia.jobboard.api.data.service.TechnologyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Technology operations", description = "Retrieve and add technology information")
@RestController
@RequestMapping("/api/v1/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    @Autowired
    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @Operation(summary = "Get all technologies")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTechnologies() {
        return ResponseEntity.status(HttpStatus.OK).body(technologyService.findAll());
    }

    @Operation(summary = "Get a technology by id")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody List<Technology> technologies) {
        return ResponseEntity.status(HttpStatus.CREATED).body(technologyService.create(technologies));
    }

    @Operation(summary = "Update a technology")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TechnologyDTO technologyDTO, @PathVariable(value = "id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(technologyService.update(technologyDTO, id));
    }

}
