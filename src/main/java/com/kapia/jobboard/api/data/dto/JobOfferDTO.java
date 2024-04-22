package com.kapia.jobboard.api.data.dto;

import com.kapia.jobboard.api.data.constants.ContractType;
import com.kapia.jobboard.api.data.constants.Experience;
import com.kapia.jobboard.api.data.constants.OperatingMode;
import com.kapia.jobboard.api.data.constants.SalaryType;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class JobOfferDTO {

    String name;

    String shortDescription;

    String description;

    ContractType contractType;

    int salary;

   String salaryCurrency;

    SalaryType salaryType;

    Experience experience;

    OperatingMode operatingMode;

   Date expiresAt;

    long companyId;

    long addressId;

    Map<Long, String> technologies;

    public JobOfferDTO() {
    }

    public JobOfferDTO(String name, String shortDescription, String description, ContractType contractType, int salary, String salaryCurrency, SalaryType salaryType, Experience experience, OperatingMode operatingMode, Date expiresAt, long companyId, long addressId, Map<Long, String> technologies) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.contractType = contractType;
        this.salary = salary;
        this.salaryCurrency = salaryCurrency;
        this.salaryType = salaryType;
        this.experience = experience;
        this.operatingMode = operatingMode;
        this.expiresAt = expiresAt;
        this.companyId = companyId;
        this.addressId = addressId;
        this.technologies = technologies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
      this.name = name;
   }

   public String getShortDescription() {
      return shortDescription;
   }

   public void setShortDescription(String shortDescription) {
      this.shortDescription = shortDescription;
   }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public SalaryType getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(SalaryType salaryType) {
        this.salaryType = salaryType;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public OperatingMode getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(OperatingMode operatingMode) {
        this.operatingMode = operatingMode;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public long getCompanyId() {
      return companyId;
   }

   public void setCompanyId(long companyId) {
      this.companyId = companyId;
   }

   public long getAddressId() {
      return addressId;
   }

   public void setAddressId(long addressId) {
      this.addressId = addressId;
   }

   public Map<Long, String> getTechnologies() {
      return technologies;
   }

   public void setTechnologies(Map<Long, String> technologies) {
      this.technologies = technologies;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
       JobOfferDTO that = (JobOfferDTO) o;
      return salary == that.salary && companyId == that.companyId && addressId == that.addressId && Objects.equals(name, that.name) && Objects.equals(shortDescription, that.shortDescription) && Objects.equals(description, that.description) && Objects.equals(contractType, that.contractType) && Objects.equals(salaryCurrency, that.salaryCurrency) && salaryType == that.salaryType && Objects.equals(experience, that.experience) && operatingMode == that.operatingMode && Objects.equals(expiresAt, that.expiresAt) && Objects.equals(technologies, that.technologies);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name, shortDescription, description, contractType, salary, salaryCurrency, salaryType, experience, operatingMode, expiresAt, companyId, addressId, technologies);
   }
}
