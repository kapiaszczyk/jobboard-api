package com.kapia.jobboard.api.data.specifications;

import com.kapia.jobboard.api.data.model.JobOffer;
import com.kapia.jobboard.api.data.searchcriteria.JobOfferSearchCriteria;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.Set;


public class JobOfferSpecifications {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOfferSpecifications.class);

    public static Specification<JobOffer> createJobOfferSpecification(JobOfferSearchCriteria criteria) {

        LOGGER.info("Creating job offer specification with criteria: {}", criteria.toJSON());

        return Specification.where(nameContains(criteria.getName()))
                .and(companyNameContains(criteria.getCompanyName()))
                .and(locationContains(criteria.getLocation()))
                .and(technologiesIn(criteria.getTechnologies()))
                .and(operatingModeIn(criteria.getOperatingMode()))
                .and(contractTypeIn(criteria.getContractType()))
                .and(experienceIn(criteria.getExperience()))
                .and(salaryIsBetween(criteria.getSalaryMin(), criteria.getSalaryMax()));
    }

    public static Specification<JobOffer> nameContains(Optional<String> name) {
        return (root, query, builder) -> {
            return name.map(n -> builder.like(root.get("name"), "%" + n + "%")).orElse(null);
        };
    }

    public static Specification<JobOffer> companyNameContains(Optional<String> companyName) {
        return (root, query, builder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("company", JoinType.LEFT).fetch("addresses", JoinType.LEFT);
            }
            return companyName.map(cn -> builder.like(root.get("company").get("name"), "%" + cn + "%")).orElse(null);
        };
    }

    public static Specification<JobOffer> locationContains(Optional<String> location) {
        return (root, query, builder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("address", JoinType.LEFT);
            }
            return location.map(l -> builder.equal(root.get("address").get("city"), l)).orElse(null);
        };
    }

    public static Specification<JobOffer> technologiesIn(Set<String> technologies) {
        if (CollectionUtils.isEmpty(technologies)) {
            return null;
        }
        return (root, query, builder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("technologies", JoinType.LEFT).fetch("technology", JoinType.LEFT);
            }
            return root.get("technologies").get("technology").get("name").in(technologies);
        };
    }

    public static Specification<JobOffer> operatingModeIn(Set<String> operatingModes) {
        if (CollectionUtils.isEmpty(operatingModes)) {
            return null;
        }
        return (root, query, builder) -> {
            return root.get("operatingMode").in(operatingModes);
        };
    }

    public static Specification<JobOffer> contractTypeIn(Set<String> contractType) {
        if (CollectionUtils.isEmpty(contractType)) {
            return null;
        }
        return (root, query, builder) -> {
            return root.get("contractType").in(contractType);
        };
    }

    public static Specification<JobOffer> experienceIn(Set<String> experience) {
        if (CollectionUtils.isEmpty(experience)) {
            return null;
        }
        return (root, query, builder) -> {
            return root.get("experience").in(experience);
        };
    }

    public static Specification<JobOffer> salaryIsBetween(Optional<Integer> salaryMin, Optional<Integer> salaryMax) {
        return (root, query, builder) -> {
            if (salaryMin.isPresent() && salaryMax.isPresent()) {
                return builder.between(root.get("salary"), salaryMin.get(), salaryMax.get());
            } else if (salaryMin.isPresent()) {
                return builder.greaterThanOrEqualTo(root.get("salary"), salaryMin.get());
            } else if (salaryMax.isPresent()) {
                return builder.lessThanOrEqualTo(root.get("salary"), salaryMax.get());
            } else {
                return null;
            }
        };
    }

}
