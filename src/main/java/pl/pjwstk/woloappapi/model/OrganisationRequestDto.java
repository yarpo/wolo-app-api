package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name cannot exceed 200 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 9, message = "Phone number must be 9 digits")
    private String phoneNumber;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Home number is required")
    private String homeNum;

    private String addressDescription;

    @NotNull(message = "District must be chosen")
    private Long districtId;

    private Long moderatorId;

    private String logoUrl;

}
