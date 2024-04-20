package com.kapia.jobboard.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kapia.jobboard.api.annotation.LogoSizeConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.Arrays;
import java.util.Set;

@Entity(name = "technology")
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    @Length(min = 3, max = 255)
    private String name;

    @NotBlank
    @NotEmpty
    @Length(min = 10, max = 1000)
    private String description;

    @LogoSizeConstraint
    private byte[] logo;

    @OneToMany(mappedBy = "technology")
    @JsonManagedReference
    @JsonIgnore
    private Set<JobOfferTechnology> jobOfferTechnologies;

    public Technology() {
    }

    public Technology(Long id, String name, String description, byte[] logo, Set<JobOfferTechnology> jobOfferTechnologies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.jobOfferTechnologies = jobOfferTechnologies;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Set<JobOfferTechnology> getJobOfferTechnologies() {
        return jobOfferTechnologies;
    }

    public void setJobOfferTechnologies(Set<JobOfferTechnology> jobOfferTechnologies) {
        this.jobOfferTechnologies = jobOfferTechnologies;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", logo=" + Arrays.toString(logo) +
                ", jobOfferTechnologies=" + jobOfferTechnologies +
                '}';
    }
}

