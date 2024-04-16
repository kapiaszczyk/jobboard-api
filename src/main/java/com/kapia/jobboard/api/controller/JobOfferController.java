package com.kapia.jobboard.api.controller;

import com.kapia.jobboard.api.payload.JobOfferRequest;
import com.kapia.jobboard.api.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-offer")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    @Autowired
    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @GetMapping
    public ResponseEntity<?> getAllJobOffers() {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findAllJobOffers());
    }

    @GetMapping("/basic")
    public ResponseEntity<?> getAllJobOffersProjected() {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findAllBasicProjectedBy());
    }

    @GetMapping("/basic/{id}")
    public ResponseEntity<?> getJobOfferProjectedById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findBasicProjectedById(id));
    }

    @GetMapping("/detailed")
    public ResponseEntity<?> getAllJobOffersDetailedProjected() {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findAllDetailedProjectedBy());
    }

    @GetMapping("/detailed/{id}")
    public ResponseEntity<?> getJobOfferDetailedProjectedById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findDetailedProjectedById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobOfferById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.findJobOfferById(id));
    }

    @PostMapping
    public ResponseEntity<?> addJobOffer(@RequestBody JobOfferRequest jobOfferRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobOfferService.add(jobOfferRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobOffer(@PathVariable Long id, @RequestBody JobOfferRequest jobOfferRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.update(jobOfferRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobOffer(@PathVariable Long id) {
        jobOfferService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
