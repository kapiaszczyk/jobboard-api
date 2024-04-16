package com.kapia.jobboard.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Set;

@Entity(name = "job_offer")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String shortDescription;

    @NotNull
    private String description;

    @NotNull
    private String contractType;

    @NotNull
    private int salary;

    @NotNull
    private String salaryCurrency;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    @NotNull
    private String experience;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OperatingMode operatingMode;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    Date expiresAt;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Address address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Company company;

    @OneToMany(
            mappedBy = "jobOffer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<JobOfferTechnology> technologies;

    public JobOffer() {
    }

    public JobOffer(Long id, String name, String shortDescription, String description, String contractType, int salary, String salaryCurrency, SalaryType salaryType, String experience, OperatingMode operatingMode, Date createdAt, Date updatedAt, Date expiresAt, Address address, Company company, Set<JobOfferTechnology> technologies) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.contractType = contractType;
        this.salary = salary;
        this.salaryCurrency = salaryCurrency;
        this.salaryType = salaryType;
        this.experience = experience;
        this.operatingMode = operatingMode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.expiresAt = expiresAt;
        this.address = address;
        this.company = company;
        this.technologies = technologies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public OperatingMode getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(OperatingMode operatingMode) {
        this.operatingMode = operatingMode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<JobOfferTechnology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<JobOfferTechnology> technologies) {
        this.technologies = technologies;
    }

    public enum SalaryType {
        hourly,
        monthly,
        annual,
        other
    }

    public enum OperatingMode {
        remote,
        hybrid,
        onsite
    }
}
