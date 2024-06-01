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
public class EventEditRequestDto {

    @NotNull
    @NotBlank(message = "Name in polish is required")
    @Size(max = 250, message = "Name in polish cannot exceed 250 characters")
    private String namePL;

    @NotNull
    @NotBlank(message = "Name in english is required")
    @Size(max = 250, message = "Name in english cannot exceed 250 characters")
    private String nameEN;

    @NotNull
    @NotBlank(message = "Name in ukrainian is required")
    @Size(max = 250, message = "Name in ukrainian cannot exceed 250 characters")
    private String nameUA;

    @NotNull
    @NotBlank(message = "Name in russian is required")
    @Size(max = 250, message = "Name in russian cannot exceed 250 characters")
    private String nameRU;

    @NotBlank(message = "Description in polish is required")
    @NotNull
    private String descriptionPL;

    @NotBlank(message = "Description in english is required")
    @NotNull
    private String descriptionEN;

    @NotBlank(message = "Description in ukrainian is required")
    @NotNull
    private String descriptionUA;

    @NotBlank(message = "Description in russian is required")
    @NotNull
    private String descriptionRU;

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
    private List<ShiftEditRequestDto> shifts;

    private Long cityId;

    private Boolean mailSend;

}
