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

/**
 * This class is responsible for handling technology related operations.
 * It provides endpoints for retrieving and adding technology information.
 */
@Tag(name = "Technology operations", description = "Retrieve and add technology information")
@RestController
@RequestMapping("/api/v1/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    /**
     * Constructor for the TechnologyController.
     *
     * @param technologyService The service to be used for technology related operations.
     */
    @Autowired
    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    /**
     * This method is responsible for retrieving all technologies.
     *
     * @return A list of all technologies.
     */
    @Operation(summary = "Get all technologies")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTechnologies() {
        return ResponseEntity.status(HttpStatus.OK).body(technologyService.findAll());
    }

    /**
     * This method is responsible for adding a technology.
     *
     * @param technologies The technology to be added.
     * @return The added technology.
     */
    @Operation(summary = "Add a technology")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody List<Technology> technologies) {
        return ResponseEntity.status(HttpStatus.CREATED).body(technologyService.create(technologies));
    }

    /**
     * This method is responsible for updating a technology.
     *
     * @param technologyDTO The technology to be updated.
     * @param id            The id of the technology to be updated.
     * @return The updated technology.
     */
    @Operation(summary = "Update a technology")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TechnologyDTO technologyDTO, @PathVariable(value = "id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(technologyService.update(technologyDTO, id));
    }

    /**
     * This method is responsible for fetching technology by id.
     *
     * @param id The id of the technology to be fetched.
     * @return The technology with the specified id.
     */
    @Operation(summary = "Get a technology by id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTechnologyById(@PathVariable(value = "id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(technologyService.findById(id));
    }

}
