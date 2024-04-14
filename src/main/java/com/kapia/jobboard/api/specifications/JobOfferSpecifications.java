package com.kapia.jobboard.api.specifications;

import com.kapia.jobboard.api.model.*;
import com.kapia.jobboard.api.searchcriteria.JobOfferSearchCriteria;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.Set;


public class JobOfferSpecifications {

    public static Specification<JobOffer> createJobOfferSpecification(JobOfferSearchCriteria criteria) {
        return Specification.where(nameContains(criteria.getName()))
                .and(companyNameContains(criteria.getCompanyName()))
                .and(locationContains(criteria.getLocation()))
                .and(technologiesIn(criteria.getTechnologies()));
    }

    public static Specification<JobOffer> nameContains(Optional<String> name) {
        return (root, query, builder) -> {
            return name.map(n -> builder.like(root.get("name"), "%" + n + "%")).orElse(null);
        };
    }

    public static Specification<JobOffer> companyNameContains(Optional<String> companyName) {
        return (root, query, builder) -> {
            return companyName.map(cn -> builder.like(root.get("company").get("name"), "%" + cn + "%")).orElse(null);
        };
    }

    public static Specification<JobOffer> locationContains(Optional<String> location) {
        return (root, query, builder) -> {
            Join<JobOffer, Company> companyJoin = root.join("company");
            Join<Company, Address> addressJoin = companyJoin.join("addresses");
            return location.map(l -> builder.like(addressJoin.get("location"), "%" + l + "%")).orElse(null);
        };
    }

    public static Specification<JobOffer> technologiesIn(Set<String> technologies) {
        if (CollectionUtils.isEmpty(technologies)) {
            return null;
        }
        return (root, query, builder) -> {
            Join<JobOffer, JobOfferTechnology> jobTechnologyJoin = root.join("technologies");
            Join<JobOfferTechnology, Technology> technologyJoin = jobTechnologyJoin.join("technology");
            return technologyJoin.get("name").in(technologies);
        };
    }

}
