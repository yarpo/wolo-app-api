package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class OrganisationResponseAdminDto {

    private Long id;

    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private String email;

    private String phoneNumber;

    private String street;

    private String homeNum;

    private String logoUrl;

    private boolean archived;

}
