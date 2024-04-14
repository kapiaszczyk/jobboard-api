package com.kapia.jobboard.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kapia.jobboard.api.model.JobOfferTechnology;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

public class TechnologyDTO {

    private String name;

    private String description;

    private byte[] logo;

    public TechnologyDTO() {
    }

    public TechnologyDTO(String name, String description, byte[] logo) {
        this.name = name;
        this.description = description;
        this.logo = logo;
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

}
