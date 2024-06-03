package com.kapia.jobboard.api.data.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kapia.jobboard.api.data.annotation.LogoSizeConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

/**
 * This class represents a company.
 */
@Entity(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Length(max = 255)
    private String name;

    @NotNull
    @NotBlank
    @Length(min = 50, max = 5000)
    private String description;

    @NotNull
    @NotBlank
    @URL
    @Length(max = 255)
    private String website;

    @Email
    @NotNull
    @NotBlank
    @Length(max = 255)
    private String email;

    @LogoSizeConstraint
    private byte[] logo;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Address> addresses;

    /**
     * Default constructor.
     */
    public Company() {
    }

    /**
     * Constructor.
     *
     * @param id The company ID.
     * @param name The company name.
     * @param description The company description.
     * @param website The company website.
     * @param email The company email.
     * @param logo The company logo.
     * @param addresses The company addresses.
     */
    public Company(Long id, String name, String description, String website, String email, byte[] logo, Set<Address> addresses) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.email = email;
        this.logo = logo;
        this.addresses = addresses;
    }

    /**
     * Get the company ID.
     * @return The company ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the company ID.
     * @param id The company ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the company name.
     * @return The company name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the company name.
     * @param name The company name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the company description.
     * @return The company description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the company description.
     * @param description The company description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the company website.
     * @return The company website.
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Set the company website.
     * @param website The company website.
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Get the company email.
     * @return The company email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the company email.
     * @param email The company email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the company logo.
     * @return The company logo.
     */
    public byte[] getLogo() {
        return logo;
    }

    /**
     * Set the company logo.
     * @param logo The company logo.
     */
    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    /**
     * Get the company addresses.
     * @return The company addresses.
     */
    public Set<Address> getAddresses() {
        return addresses;
    }

    /**
     * Set the company addresses.
     * @param addresses The company addresses.
     */
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * Add addresses to the company.
     * @param addresses The addresses to add.
     */
    public void addAddresses(Set<Address> addresses) {

        if(this.addresses == null) {
            this.setAddresses(addresses);
        }
        else {
            this.addresses.addAll(addresses);
        }

        for (Address address : addresses) {
            address.setCompany(this);
        }
    }

    /**
     * Get the string representation of the company.
     * @return The string representation of the company.
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", logo=" + logo +
                '}';
    }
}
