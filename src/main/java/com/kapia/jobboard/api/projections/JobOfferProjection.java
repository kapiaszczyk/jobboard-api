package com.kapia.jobboard.api.projections;

public interface JobOfferProjection {

    String getName();

    String getShortDescription();

    String getContractType();

    int getSalary();

    String getSalaryCurrency();

    String getExperience();

    String getOperatingMode();

}
