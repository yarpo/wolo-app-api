package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class EventResponseDto {

    @Schema(name = "Organisation ID", example = "1")
    @NotNull
    private Long id;

    @Schema(name = "Organisation", example = "Compassion Paws: Animal Shelter Support Initiative")
    @NotNull
    private String name;

    private String organisation;

    private boolean isPeselVerificationRequired;

    @NotNull
    @NotBlank(message = "Street is required")
    @Size(max = 50, message = "Street cannot exceed 50 characters")
    private String street;

    @NotNull
    @NotBlank(message = "Home number is required")
    @Size(max = 10, message = "Home number cannot exceed 10 characters")
    private String homeNum;

    @NotNull(message = "District must be chosen")
    private String district;

    @NotNull(message = "Category ID is required")
    private List<CategoryDto> categories;

    @NotNull
    @Schema(name = "City name", example = "New York City")
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    private String city;

    @Size(max = 255, message = "Image Url cannot exceed 255 characters")
    private String imageUrl;

    @NotNull(message = "Shifts are required")
    @Size(min = 1, message = "At least one shift is required")
    @Valid
    private List<ShiftDto> shifts;
}
