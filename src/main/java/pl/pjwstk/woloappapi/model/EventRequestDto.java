package pl.pjwstk.woloappapi.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name cannot exceed 250 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private Long organisationId;

    @NotNull(message = "Category ID is required")
    private List<Long> categories;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

    @NotBlank(message = "Street is required")
    @Size(max = 200, message = "Street cannot exceed 200 characters")
    private String street;

    @NotBlank(message = "Home number is required")
    @Size(max = 20, message = "Home number cannot exceed 20 characters")
    private String homeNum;

    @NotNull(message = "District must be chosen")
    private Long districtId;

    private String addressDescription;

    private String imageUrl;

    @NotNull(message = "Shifts are required")
    @Size(min = 1, message = "At least one shift is required")
    @Valid
    private List<ShiftDto> shifts;
}
