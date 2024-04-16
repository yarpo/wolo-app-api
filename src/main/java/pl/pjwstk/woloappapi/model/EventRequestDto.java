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
public class EventRequestDto {

    @NotNull
    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name cannot exceed 250 characters")
    @Schema(name = "Event title", example = "Animal shelter assistance")
    private String name;

    @Schema(name = "Event description", example = """
            Are you passionate about making a positive impact on the lives of animals in need? Join us in our mission\040
            to provide compassionate care and support for animals at our local animal shelter.\040
            As an Animal Shelter Assistant, you play a crucial role in ensuring the well-being\040
            and happiness of animals awaiting their forever homes.

            Responsibilities:

                Animal Care:
                    Feed, groom, and provide basic care for shelter animals.
                    Ensure clean and sanitary living conditions for all animals.

                Assistance with Adoptions:
                    Interact with potential adopters and provide information about available animals.
                    Assist in the adoption process, ensuring a smooth transition for both the animal and the new owner.""")
    @NotBlank(message = "Description is required")
    @NotNull
    private String description;

    @NotNull(message = "Organisation ID is required")
    @Schema(name = "Organisation", example = "Compassion Paws: Animal Shelter Support Initiative")
    private Long organisationId;

    @NotNull(message = "Category ID is required")
    private List<Long> categories;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

    @Size(max = 255, message = "Image Url cannot exceed 255 characters")
    private String imageUrl;

    @NotNull(message = "Shifts are required")
    @Size(min = 1, message = "At least one shift is required")
    @Valid
    private List<ShiftRequestDto> shifts;

}
