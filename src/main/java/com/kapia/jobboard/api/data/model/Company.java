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

    public Company() {
    }

    public Company(Long id, String name, String description, String website, String email, byte[] logo, Set<Address> addresses) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.email = email;
        this.logo = logo;
        this.addresses = addresses;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

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
