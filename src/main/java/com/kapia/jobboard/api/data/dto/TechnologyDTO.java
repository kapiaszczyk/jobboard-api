package com.kapia.jobboard.api.data.dto;

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
