package com.kapia.jobboard.api.projections;

import java.util.Date;
import java.util.Set;

public interface JobOfferDetailedView {

    String getName();

    String getSalary();

    String getSalaryCurrency();

    String getContractType();

    String getExperience();

    String getOperatingMode();

    Date getExpiresAt();

    Set<JOTView> getTechnologies();

    AddressView getAddress();

    CompanyView getCompany();

    interface JOTView {
        TechnologyView getTechnology();

        String getDegreeOfKnowledge();
    }

    interface TechnologyView {
        String getName();
    }

    interface AddressView {
        String getCity();

        String getCountry();
    }

    interface CompanyView {
        String getName();
    }

}
