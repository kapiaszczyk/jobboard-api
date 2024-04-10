package com.kapia.jobboard.api.controller;

import com.kapia.jobboard.api.searchcriteria.JobOfferSearchCriteria;
import com.kapia.jobboard.api.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/job-offer")
public class JobOfferController {

    private final JobOfferService jobOfferService;


    @Autowired
    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    // Get all job offers
    @GetMapping
    public ResponseEntity<?> getAllJobOffers() {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findAllJobOffers());
    }

    // Get job offer by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobOfferById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findJobOfferById(id));
    }

    // Get offer via criteria
    @GetMapping("/criteria")
    public ResponseEntity<?> getJobOfferByCriteria(

            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> company_name,
            @RequestParam(required = false) Optional<String> location,
            @RequestParam(required = false) Set<String> technologies

    ) {

        JobOfferSearchCriteria jobOfferSearchCriteria = JobOfferSearchCriteria.builder()
                .name(name)
                .companyName(company_name)
                .location(location)
                .technologies(technologies)
                .build();

        System.out.println(jobOfferSearchCriteria.toString());

        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findJobOfferByCriteria(jobOfferSearchCriteria));

    }


}
