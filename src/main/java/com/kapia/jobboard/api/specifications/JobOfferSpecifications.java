package com.kapia.jobboard.api.specifications;

import com.kapia.jobboard.api.model.JobOffer;
import com.kapia.jobboard.api.searchcriteria.JobOfferSearchCriteria;
import jakarta.persistence.criteria.JoinType;
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
            root.fetch("company", JoinType.LEFT).fetch("addresses", JoinType.LEFT);
            return companyName.map(cn -> builder.like(root.get("company").get("name"), "%" + cn + "%")).orElse(null);
        };
    }

    public static Specification<JobOffer> locationContains(Optional<String> location) {
        return (root, query, builder) -> {
            root.fetch("address", JoinType.LEFT);
            return location.map(l -> builder.like(root.get("address").get("city"), "%" + l + "%")).orElse(null);
        };
    }

    public static Specification<JobOffer> technologiesIn(Set<String> technologies) {
        if (CollectionUtils.isEmpty(technologies)) {
            return null;
        }
        return (root, query, builder) -> {
            root.fetch("technologies", JoinType.LEFT).fetch("technology", JoinType.LEFT);
            return root.get("technologies").get("technology").get("name").in(technologies);

        };
    }

}
