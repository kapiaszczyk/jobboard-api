package com.kapia.jobboard.api.searchcriteria;

import java.util.Optional;
import java.util.Set;

public class JobOfferSearchCriteria {

    private Optional<String> name;
    private Optional<String> companyName;
    private Optional<String> location;
    private Set<String> technologies;

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

    // Create a builder for the JobOfferSearchCriteria class
    public static JobOfferSearchCriteriaBuilder builder() {
        return new JobOfferSearchCriteriaBuilder();
    }

    // Create a builder class for the JobOfferSearchCriteria class
    public static class JobOfferSearchCriteriaBuilder {
        private Optional<String> name;
        private Optional<String> companyName;
        private Optional<String> location;
        private Set<String> technologies;

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

        public JobOfferSearchCriteria build() {
            JobOfferSearchCriteria jobOfferSearchCriteria = new JobOfferSearchCriteria();
            jobOfferSearchCriteria.setName(name);
            jobOfferSearchCriteria.setCompanyName(companyName);
            jobOfferSearchCriteria.setLocation(location);
            jobOfferSearchCriteria.setTechnologies(technologies);
            return jobOfferSearchCriteria;
        }

        @Override
        public String toString() {
            return "JobOfferSearchCriteriaBuilder{" +
                    "name=" + name +
                    ", companyName=" + companyName +
                    ", location=" + location +
                    ", technologies=" + technologies +
                    '}';
        }
    }

}
