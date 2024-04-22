package com.kapia.jobboard.api.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JobOfferTechnologyKey implements Serializable {

    @Column(name = "job_offer_id")
    private Long jobOfferId;

    @Column(name = "technology_id")
    private Long technologyId;

    public JobOfferTechnologyKey() {
    }

    public JobOfferTechnologyKey(Long jobOfferId, Long technologyId) {
        this.jobOfferId = jobOfferId;
        this.technologyId = technologyId;
    }

    public Long getJobOfferId() {
        return jobOfferId;
    }

    public void setJobOfferId(Long jobOfferId) {
        this.jobOfferId = jobOfferId;
    }

    public Long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Long technologyId) {
        this.technologyId = technologyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobOfferTechnologyKey that = (JobOfferTechnologyKey) o;
        return jobOfferId.equals(that.jobOfferId) && technologyId.equals(that.technologyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobOfferId, technologyId);
    }
}
