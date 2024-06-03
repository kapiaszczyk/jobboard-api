package com.kapia.jobboard.api.data.projections;

/**
 * Represents a basic view of a job offer.
 */
public interface JobOfferBasicView {

    String getName();

    String getContractType();

    int getSalary();

    String getSalaryCurrency();

    String getExperience();

    String getOperatingMode();

    String getCompanyName();

    String getAddressCity();

}
