package pl.pjwstk.woloappapi.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDto {

    @NotNull
    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name cannot exceed 250 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @NotNull
    private String description;

    @NotNull(message = "Organisation ID is required")
    private Long organisationId;

    @NotNull(message = "Category ID is required")
    private List<Long> categories;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

    @NotNull
    private LocalDate date;

    @Size(max = 255, message = "Image Url cannot exceed 255 characters")
    private String imageUrl;

    @NotNull(message = "Shifts are required")
    @Size(min = 1, message = "At least one shift is required")
    @Valid
    private List<ShiftRequestDto> shifts;

    private Long cityId;

}
