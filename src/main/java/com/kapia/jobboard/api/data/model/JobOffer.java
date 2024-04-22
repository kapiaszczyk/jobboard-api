package com.kapia.jobboard.api.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kapia.jobboard.api.data.annotation.*;
import com.kapia.jobboard.api.data.constants.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.Set;

@Entity(name = "job_offer")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Length(min = 10, max = 255)
    private String name;

    @NotBlank
    @NotNull
    @Length(min = 50, max = 255)
    private String shortDescription;

    @NotBlank
    @NotNull
    @Length(min = 255, max = 5000)
    private String description;

    @NotNull
    @ContractTypeSubset(anyOf = {
            ContractType.CONTRACT,
            ContractType.FULL_TIME,
            ContractType.INTERNSHIP,
            ContractType.OTHER,
            ContractType.PART_TIME,
            ContractType.TEMPORARY,
            ContractType.B2B})
    private ContractType contractType;

    @NotNull
    @Positive
    private int salary;

    @CurrencySubset(anyOf = Currencies.VALID_CURRENCIES)
    private String salaryCurrency;


    @NotNull
    @SalaryTypeSubset(anyOf = {
            SalaryType.ANNUAL,
            SalaryType.HOURLY,
            SalaryType.MONTHLY,
            SalaryType.OTHER})
    private SalaryType salaryType;

    @NotNull
    @ExperienceSubset(anyOf = {
            Experience.INTERN,
            Experience.JUNIOR,
            Experience.REGULAR,
            Experience.MID,
            Experience.SENIOR,
            Experience.EXPERT,
            Experience.ARCHITECT,
            Experience.OTHER
    })
    private Experience experience;

    @NotNull
    @OperatingModeSubset(anyOf = {
            OperatingMode.HYBRID,
            OperatingMode.REMOTE,
            OperatingMode.ONSITE})
    private OperatingMode operatingMode;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @FutureDateConstraint
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

    public JobOffer(String name, String shortDescription, String description, ContractType contractType, int salary,
                    String salaryCurrency, SalaryType salaryType, Experience experience, OperatingMode operatingMode,
                    Date expiresAt, Address address, Company company) {
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
        this.address = address;
        this.company = company;
    }

    public JobOffer(Long id, String name, String shortDescription, String description, ContractType contractType, int salary, String salaryCurrency, SalaryType salaryType, Experience experience, OperatingMode operatingMode, Date createdAt, Date updatedAt, Date expiresAt, Address address, Company company, Set<JobOfferTechnology> technologies) {
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

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
