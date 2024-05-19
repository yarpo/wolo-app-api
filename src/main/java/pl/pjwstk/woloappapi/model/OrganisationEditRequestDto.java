package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationEditRequestDto {

    @NotNull
    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name cannot exceed 200 characters")
    private String name;

    @NotBlank(message = "Description PL is required")
    private String descriptionPL;

    @NotBlank(message = "Description EN is required")
    private String descriptionEN;

    @NotBlank(message = "Description UA is required")
    private String descriptionUA;

    @NotBlank(message = "Description RU is required")
    private String descriptionRU;

    @NotNull
    @NotBlank(message = "Email is required")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "[0-9]{9}", message = "Phone number should consist of 9 digits")
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

    @NotNull(message = "District must be chosen")
    private Long districtId;

    @NotNull(message = "City must be chosen")
    private Long cityId;

    @NotNull(message = "Moderator must be chosen")
    private Long moderatorId;

    @Size(max = 255, message = "Logo Url cannot exceed 255 characters")
    private String logoUrl;
}
