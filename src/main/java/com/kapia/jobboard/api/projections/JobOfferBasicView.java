package com.kapia.jobboard.api.projections;

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
