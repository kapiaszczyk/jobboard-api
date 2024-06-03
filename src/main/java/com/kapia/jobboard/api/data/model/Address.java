package com.kapia.jobboard.api.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

/**
 * This class represents an address.
 */
@Entity(name = "address")
public class Address {

    /**
     * The ID of the address.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The street of the address.
     */
    @NotNull
    @NotBlank
    @Length(min = 3, max = 255)
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-']+$")
    private String street;

    /**
     * The city of the address.
     */
    @NotNull
    @NotBlank
    @Length(min = 3, max = 255)
    @Pattern(regexp = "^[a-zA-Z\\s\\-']+$")
    private String city;

    /**
     * The postal code of the address.
     */
    @NotNull
    @Length(min = 3, max = 255)
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$")
    private String postalCode;

    /**
     * The country of the address.
     */
    @NotNull
    @NotBlank
    @Length(min = 3, max = 255)
    @Pattern(regexp = "^[a-zA-Z\\s\\-']+$")
    private String country;

    /**
     * The company that the address belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;

    /**
     * Default constructor for the address.
     */
    public Address() {
    }

    /**
     * Constructor for the address.
     *
     * @param id The ID of the address.
     * @param street The street of the address.
     * @param city The city of the address.
     * @param postalCode The postal code of the address.
     * @param country The country of the address.
     * @param company The company that the address belongs to.
     */
    public Address(Long id, String street, String city, String postalCode, String country, Company company) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.company = company;
    }

    /**
     * Get the ID of the address.
     * @return The ID of the address.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the address.
     * @param id The ID of the address.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the street of the address.
     * @return The street of the address.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set the street of the address.
     * @param street The street of the address.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get the city of the address.
     * @return The city of the address.
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the city of the address.
     * @param city The city of the address.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the postal code of the address.
     * @return The postal code of the address.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Set the postal code of the address.
     * @param postalCode The postal code of the address.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Get the country of the address.
     * @return The country of the address.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the country of the address.
     * @param country The country of the address.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the company that the address belongs to.
     * @return The company that the address belongs to.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Set the company that the address belongs to.
     * @param company The company that the address belongs to.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Get the string representation of the address.
     * @return The string representation of the address.
     */
    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
