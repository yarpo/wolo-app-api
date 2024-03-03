package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name cannot exceed 200 characters")
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

    @NotNull
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 9, message = "Phone number must be 9 digits")
    private String phoneNumber;

    @NotNull
    @NotBlank(message = "Street is required")
    @Size(max = 50, message = "Street cannot exceed 50 characters")
    private String street;

    @NotNull
    @NotBlank(message = "Home number is required")
    @Size(max = 10, message = "Home number cannot exceed 10 characters")
    private String homeNum;

    @Size(max = 255, message = "Logo Url cannot exceed 255 characters")
    private String logoUrl;
}
