package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationResponseDto {

    @Schema(name = "Organisation Name", example = "Animal Helpers")
    private String name;

    @Schema(name = "Organization Description", example = """
            Are you passionate about making a positive impact on the lives of animals in need? Animal Helpers is 
            dedicated to providing compassionate care and support for animals at our local animal shelter. 
            As part of our organization, you'll have the opportunity to play a vital role in ensuring the well-being 
            and happiness of animals awaiting their forever homes.

            Our Mission:
                - Provide shelter and care for animals in need.
                - Facilitate adoptions and find loving homes for our animals.
                - Educate the community about responsible pet ownership and animal welfare.

            Volunteer Opportunities:
                - Animal Care: Assist in feeding, grooming, and providing basic care for shelter animals.
                - Adoption Assistance: Interact with potential adopters and facilitate the adoption process.
                - Community Outreach: Participate in events and educational programs to promote animal welfare.

            Join us today and make a difference in the lives of animals in need!""")
    @NotBlank(message = "Description is required")
    private String description;

    private String email;

    private String phoneNumber;

    private String street;

    private String homeNum;

    @Size(max = 255, message = "Logo Url cannot exceed 255 characters")
    private String logoUrl;
}
