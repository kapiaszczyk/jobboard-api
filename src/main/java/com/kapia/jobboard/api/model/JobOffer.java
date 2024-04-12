package com.kapia.jobboard.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(
            mappedBy = "jobOffer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<JobOfferTechnology> technologies;

    public JobOffer() {
    }

    public JobOffer(Long id, String name, String shortDescription, String description, String contractType, int salary, String salaryCurrency, SalaryType salaryType, String experience, OperatingMode operatingMode, Address address, Company company, Set<JobOfferTechnology> technologies) {
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
