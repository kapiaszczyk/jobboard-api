package com.kapia.jobboard.api.data.controller;

import com.kapia.jobboard.api.data.constants.SortingCriteria;
import com.kapia.jobboard.api.data.constants.SortingOrder;
import com.kapia.jobboard.api.data.dto.JobOfferDTO;
import com.kapia.jobboard.api.data.projections.JobOfferBasicView;
import com.kapia.jobboard.api.data.searchcriteria.JobOfferSearchCriteria;
import com.kapia.jobboard.api.data.service.JobOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * This class is responsible for handling job offer related operations.
 * It provides endpoints for retrieving, adding, updating and deleting job offers.
 */
@Tag(name = "Job offer operations", description = "Retrieve, add, update and delete job offers")
@RestController
@RequestMapping("/api/v1/job-offers")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    /**
     * Constructor for the JobOfferController.
     *
     * @param jobOfferService The service to be used for job offer related operations.
     */
    @Autowired
    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    /**
     * This method is responsible for retrieving all job offers.
     *
     * @return A list of all job offers.
     */
    @Operation(summary = "Get all job offers")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllJobOffers() {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findAllJobOffers());
    }

    /**
     * This method is responsible for retrieving a job offer by id.
     *
     * @param id The id of the job offer to retrieve.
     * @return The job offer with the specified id.
     */
    @Operation(summary = "Get a job offer by id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getJobOfferById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findJobOfferById(id));
    }

    /**
     * This method is responsible for deleting a job offer by id.
     *
     * @param id The id of the job offer to delete.
     * @return A response entity with the status of the deletion.
     */
    @Operation(summary = "Delete a job offer")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteJobOffer(@PathVariable Long id) {
        jobOfferService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * This method is responsible for adding a new job offer.
     *
     * @param jobOfferDTO The job offer to be added.
     * @return The added job offer.
     */
    @Operation(summary = "Add a new job offer")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<JobOfferBasicView>> addJobOffer(@RequestBody JobOfferDTO jobOfferDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobOfferService.add(jobOfferDTO));
    }

    /**
     * This method is responsible for updating a job offer.
     *
     * @param id          The id of the job offer to update.
     * @param jobOfferDTO The job offer to be updated.
     * @return The updated job offer.
     */
    @Operation(summary = "Update a job offer")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateJobOffer(@PathVariable Long id, @RequestBody JobOfferDTO jobOfferDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.update(jobOfferDTO, id));
    }

    /**
     * This method is responsible for retrieving all job offers projected to basic view.
     *
     * @return A list of all job offers projected to basic view.
     */
    @Operation(summary = "Get all job offers projected to basic view")
    @GetMapping(value = "/basic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllJobOffersProjected() {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findAllBasicProjectedBy());
    }

    /**
     * This method is responsible for retrieving a job offer by id projected to basic view.
     *
     * @param id The id of the job offer to retrieve.
     * @return The job offer with the specified id projected to basic view.
     */
    @Operation(summary = "Get a job offer by id projected to basic view")
    @GetMapping(value = "/basic/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getJobOfferProjectedById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findBasicProjectedById(id));
    }

    /**
     * This method is responsible for retrieving all job offers projected to detailed view.
     *
     * @return A list of all job offers projected to detailed view.
     */
    @Operation(summary = "Get all job offers projected to detailed view")
    @GetMapping(value = "/detailed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllJobOffersDetailedProjected() {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findAllDetailedProjectedBy());
    }

    /**
     * This method is responsible for retrieving a job offer by id projected to detailed view.
     *
     * @param id The id of the job offer to retrieve.
     * @return The job offer with the specified id projected to detailed view.
     */
    @Operation(summary = "Get a job offer by id projected to detailed view")
    @GetMapping(value = "/detailed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getJobOfferDetailedProjectedById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findDetailedProjectedById(id));
    }

    /**
     * This method is responsible for retrieving all job offers, paged and sorted.
     *
     * @param name           The name of the job offer to retrieve.
     * @param company_name   The name of the company to retrieve job offers for.
     * @param location       The location of the job offer to retrieve.
     * @param technologies    The technologies of the job offer to retrieve.
     * @param operating_mode The operating mode of the job offer to retrieve.
     * @param contract_type  The contract type of the job offer to retrieve.
     * @param experience      The experience of the job offer to retrieve.
     * @param salary_min      The minimum salary of the job offer to retrieve.
     * @param salary_max      The maximum salary of the job offer to retrieve.
     *
     * @return A list of all job offers paginated and sorted.
     */
    @Operation(summary = "Get job offers filtered by criteria")
    @GetMapping(value = "/criteria", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getJobOfferByCriteria(

            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> company_name,
            @RequestParam(required = false) Optional<String> location,
            @RequestParam(required = false) Set<String> technologies,
            @RequestParam(required = false) Set<String> operating_mode,
            @RequestParam(required = false) Set<String> contract_type,
            @RequestParam(required = false) Set<String> experience,
            @RequestParam(required = false) Optional<Integer> salary_min,
            @RequestParam(required = false) Optional<Integer> salary_max

    ) {

        JobOfferSearchCriteria jobOfferSearchCriteria = JobOfferSearchCriteria.builder()
                .name(name)
                .companyName(company_name)
                .location(location)
                .technologies(technologies)
                .operatingMode(operating_mode)
                .contractType(contract_type)
                .experience(experience)
                .salaryMin(salary_min)
                .salaryMax(salary_max)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findJobOfferByCriteria(jobOfferSearchCriteria));

    }

    /**
     * This method is responsible for retrieving all job offers projected to detailed view, paged and sorted.
     *
     * @param name           The name of the job offer to retrieve.
     * @param company_name   The name of the company to retrieve job offers for.
     * @param location       The location of the job offer to retrieve.
     * @param technologies    The technologies of the job offer to retrieve.
     * @param operating_mode The operating mode of the job offer to retrieve.
     * @param contract_type  The contract type of the job offer to retrieve.
     * @param experience      The experience of the job offer to retrieve.
     * @param salary_min      The minimum salary of the job offer to retrieve.
     * @param salary_max      The maximum salary of the job offer to retrieve.
     *
     * @return A list of all job offers projected to detailed view, paged and sorted.
     */
    @Operation(summary = "Get job offers filtered by criteria projected to detailed view")
    @GetMapping(value = "/criteria-detailed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getJobOfferByCriteriaProjectedBy(

            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> company_name,
            @RequestParam(required = false) Optional<String> location,
            @RequestParam(required = false) Set<String> technologies,
            @RequestParam(required = false) Set<String> operating_mode,
            @RequestParam(required = false) Set<String> contract_type,
            @RequestParam(required = false) Set<String> experience,
            @RequestParam(required = false) Optional<Integer> salary_min,
            @RequestParam(required = false) Optional<Integer> salary_max

    ) {

        JobOfferSearchCriteria jobOfferSearchCriteria = JobOfferSearchCriteria.builder()
                .name(name)
                .companyName(company_name)
                .location(location)
                .technologies(technologies)
                .operatingMode(operating_mode)
                .contractType(contract_type)
                .experience(experience)
                .salaryMin(salary_min)
                .salaryMax(salary_max)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.getJobOfferByCriteriaProjectedBy(jobOfferSearchCriteria));

    }

    /**
     * This method is responsible for retrieving all job offers projected to detailed view, paged and sorted ascending by created at.
     *
     * @param name           The name of the job offer to retrieve.
     * @param company_name   The name of the company to retrieve job offers for.
     * @param location       The location of the job offer to retrieve.
     * @param technologies    The technologies of the job offer to retrieve.
     * @param operating_mode The operating mode of the job offer to retrieve.
     * @param contract_type  The contract type of the job offer to retrieve.
     * @param experience      The experience of the job offer to retrieve.
     * @param salary_min      The minimum salary of the job offer to retrieve.
     * @param salary_max      The maximum salary of the job offer to retrieve.
     *
     * @return A list of all job offers projected to detailed view, paged and sorted.
     */
    @Operation(summary = "Get job offers filtered by criteria, paged and sorted")
    @GetMapping(value = "/criteria-paged", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getJobOfferByCriteriaPageAndSortByCreatedAtAsc(

            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> company_name,
            @RequestParam(required = false) Optional<String> location,
            @RequestParam(required = false) Set<String> technologies,
            @RequestParam(required = false) Set<String> operating_mode,
            @RequestParam(required = false) Set<String> contract_type,
            @RequestParam(required = false) Set<String> experience,
            @RequestParam(required = false) Optional<Integer> salary_min,
            @RequestParam(required = false) Optional<Integer> salary_max,
            @RequestParam(required = false) Optional<Integer> page_size,
            @RequestParam(required = false) Optional<Integer> page_number,
            @RequestParam(required = false) Optional<String> sort_by,
            @RequestParam(required = false) Optional<String> sort_direction

    ) {

        JobOfferSearchCriteria jobOfferSearchCriteria = JobOfferSearchCriteria.builder()
                .name(name)
                .companyName(company_name)
                .location(location)
                .technologies(technologies)
                .operatingMode(operating_mode)
                .contractType(contract_type)
                .experience(experience)
                .salaryMin(salary_min)
                .salaryMax(salary_max)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobOfferService.findJobOfferByCriteriaPageAndSortByCreatedAtAsc(
                        jobOfferSearchCriteria,
                        page_size.orElse(10),
                        page_number.orElse(0),
                        sort_by.orElse(SortingCriteria.CREATED_AT.toString()),
                        sort_direction.orElse(SortingOrder.ASC.toString())
                ));

    }

    /**
     * This method is responsible for retrieving all job offers projected to detailed view, paged and sorted ascending by created at.
     *
     * @param name           The name of the job offer to retrieve.
     * @param company_name   The name of the company to retrieve job offers for.
     * @param location       The location of the job offer to retrieve.
     * @param technologies    The technologies of the job offer to retrieve.
     * @param operating_mode The operating mode of the job offer to retrieve.
     * @param contract_type  The contract type of the job offer to retrieve.
     * @param experience      The experience of the job offer to retrieve.
     * @param salary_min      The minimum salary of the job offer to retrieve.
     * @param salary_max      The maximum salary of the job offer to retrieve.
     * @param page_size      The number of job offers to retrieve per page.
     * @param page_number    The page number to retrieve.
     *
     * @return A list of all job offers projected to detailed view, paged and sorted.
     */
    @Operation(summary = "Get job offers filtered by criteria, paged and sorted, projected to detailed view")
    @GetMapping(value = "/criteria-detailed-paged", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getJobOfferByCriteriaPageAndSortByCreatedAtAscProjectedBy(

            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> company_name,
            @RequestParam(required = false) Optional<String> location,
            @RequestParam(required = false) Set<String> technologies,
            @RequestParam(required = false) Set<String> operating_mode,
            @RequestParam(required = false) Set<String> contract_type,
            @RequestParam(required = false) Set<String> experience,
            @RequestParam(required = false) Optional<Integer> salary_min,
            @RequestParam(required = false) Optional<Integer> salary_max,
            @RequestParam(required = false) Optional<Integer> page_size,
            @RequestParam(required = false) Optional<Integer> page_number

    ) {

        JobOfferSearchCriteria jobOfferSearchCriteria = JobOfferSearchCriteria.builder()
                .name(name)
                .companyName(company_name)
                .location(location)
                .technologies(technologies)
                .operatingMode(operating_mode)
                .contractType(contract_type)
                .experience(experience)
                .salaryMin(salary_min)
                .salaryMax(salary_max)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobOfferService.findJobOfferByCriteriaPageAndSortByCreatedAtAscProjectedBy(
                        jobOfferSearchCriteria,
                        page_size.orElse(10),
                        page_number.orElse(0)
                ));
    }

}
