package com.kapia.jobboard.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity(name = "job_offer_technology")
public class JobOfferTechnology {

    @EmbeddedId
    private JobOfferTechnologyKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("jobOfferId")
    @JoinColumn(name = "job_offer_id")
    @JsonBackReference
    private JobOffer jobOffer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("technologyId")
    @JoinColumn(name = "technology_id")
    @JsonBackReference
    private Technology technology;

    @NotNull
    private String degreeOfKnowledge;

    public JobOfferTechnology() {
    }

    public JobOfferTechnology( JobOffer jobOffer, Technology technology, String degreeOfKnowledge) {
        this.jobOffer = jobOffer;
        this.technology = technology;
        this.degreeOfKnowledge = degreeOfKnowledge;
    }

    public JobOfferTechnology(JobOfferTechnologyKey id, JobOffer jobOffer, Technology technology, String degreeOfKnowledge) {
        this.id = id;
        this.jobOffer = jobOffer;
        this.technology = technology;
        this.degreeOfKnowledge = degreeOfKnowledge;
    }

    public JobOfferTechnologyKey getId() {
        return id;
    }

    public void setId(JobOfferTechnologyKey id) {
        this.id = id;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public String getDegreeOfKnowledge() {
        return degreeOfKnowledge;
    }

    public void setDegreeOfKnowledge(String degreeOfKnowledge) {
        this.degreeOfKnowledge = degreeOfKnowledge;
    }
}
