package com.kapia.jobboard.api.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kapia.jobboard.api.data.annotation.DegreeOfKnowledgeSubset;
import com.kapia.jobboard.api.data.constants.DegreeOfKnowledge;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a job offer technology in the job board application.
 */
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
    @DegreeOfKnowledgeSubset(anyOf = {
            DegreeOfKnowledge.NONE,
            DegreeOfKnowledge.BEGINNER,
            DegreeOfKnowledge.INTERMEDIATE,
            DegreeOfKnowledge.ADVANCED,
            DegreeOfKnowledge.EXPERT
    })
    private DegreeOfKnowledge degreeOfKnowledge;

    public JobOfferTechnology() {
    }

    public JobOfferTechnology(JobOffer jobOffer, Technology technology, DegreeOfKnowledge degreeOfKnowledge) {
        this.jobOffer = jobOffer;
        this.technology = technology;
        this.degreeOfKnowledge = degreeOfKnowledge;
    }

    public JobOfferTechnology(JobOfferTechnologyKey id, JobOffer jobOffer, Technology technology, DegreeOfKnowledge degreeOfKnowledge) {
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

    public DegreeOfKnowledge getDegreeOfKnowledge() {
        return degreeOfKnowledge;
    }

    public void setDegreeOfKnowledge(DegreeOfKnowledge degreeOfKnowledge) {
        this.degreeOfKnowledge = degreeOfKnowledge;
    }
}
