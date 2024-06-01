package com.kapia.jobboard.api.data.searchcriteria;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.Set;

/**
 * Represents the search criteria for a job offer.
 */
public class JobOfferSearchCriteria {

    private Optional<String> name;
    private Optional<String> companyName;
    private Optional<String> location;
    private Set<String> technologies;
    private Set<String> operatingMode;
    private Set<String> contractType;
    private Set<String> experience;
    private Optional<Integer> salaryMin;
    private Optional<Integer> salaryMax;

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<String> getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Optional<String> companyName) {
        this.companyName = companyName;
    }

    public Optional<String> getLocation() {
        return location;
    }

    public void setLocation(Optional<String> location) {
        this.location = location;
    }

    public Set<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }

    public Set<String> getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(Set<String> operatingMode) {
        this.operatingMode = operatingMode;
    }

    public Set<String> getContractType() {
        return contractType;
    }

    public void setContractType(Set<String> contractType) {
        this.contractType = contractType;
    }

    public Set<String> getExperience() {
        return experience;
    }

    public void setExperience(Set<String> experience) {
        this.experience = experience;
    }

    public Optional<Integer> getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Optional<Integer> salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Optional<Integer> getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Optional<Integer> salaryMax) {
        this.salaryMax = salaryMax;
    }

    public static JobOfferSearchCriteriaBuilder builder() {
        return new JobOfferSearchCriteriaBuilder();
    }

    public static class JobOfferSearchCriteriaBuilder {
        private Optional<String> name;
        private Optional<String> companyName;
        private Optional<String> location;
        private Set<String> technologies;
        private Set<String> operatingMode;
        private Set<String> contractType;
        private Set<String> experience;
        private Optional<Integer> salaryMin;
        private Optional<Integer> salaryMax;

        public JobOfferSearchCriteriaBuilder name(Optional<String> name) {
            this.name = name;
            return this;
        }

        public JobOfferSearchCriteriaBuilder companyName(Optional<String> companyName) {
            this.companyName = companyName;
            return this;
        }

        public JobOfferSearchCriteriaBuilder location(Optional<String> location) {
            this.location = location;
            return this;
        }

        public JobOfferSearchCriteriaBuilder technologies(Set<String> technologies) {
            this.technologies = technologies;
            return this;
        }

        public JobOfferSearchCriteriaBuilder operatingMode(Set<String> operatingMode) {
            this.operatingMode = operatingMode;
            return this;
        }

        public JobOfferSearchCriteriaBuilder contractType(Set<String> contractType) {
            this.contractType = contractType;
            return this;
        }

        public JobOfferSearchCriteriaBuilder experience(Set<String> experience) {
            this.experience = experience;
            return this;
        }

        public JobOfferSearchCriteriaBuilder salaryMin(Optional<Integer> salaryMin) {
            this.salaryMin = salaryMin;
            return this;
        }

        public JobOfferSearchCriteriaBuilder salaryMax(Optional<Integer> salaryMax) {
            this.salaryMax = salaryMax;
            return this;
        }

        public JobOfferSearchCriteria build() {
            JobOfferSearchCriteria jobOfferSearchCriteria = new JobOfferSearchCriteria();

            jobOfferSearchCriteria.setName(name);
            jobOfferSearchCriteria.setCompanyName(companyName);
            jobOfferSearchCriteria.setLocation(location);
            jobOfferSearchCriteria.setTechnologies(technologies);
            jobOfferSearchCriteria.setOperatingMode(operatingMode);
            jobOfferSearchCriteria.setContractType(contractType);
            jobOfferSearchCriteria.setExperience(experience);
            jobOfferSearchCriteria.setSalaryMin(salaryMin);
            jobOfferSearchCriteria.setSalaryMax(salaryMax);

            return jobOfferSearchCriteria;
        }

    }

    @Override
    public String toString() {
        return "JobOfferSearchCriteria{" +
                "name=" + name +
                ", companyName=" + companyName +
                ", location=" + location +
                ", technologies=" + technologies +
                ", operatingMode=" + operatingMode +
                ", contractType=" + contractType +
                ", experience=" + experience +
                ", salaryMin=" + salaryMin +
                ", salaryMax=" + salaryMax +
                '}';
    }

    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "JSON serialization error: " + e.getMessage();
        }
    }
}
