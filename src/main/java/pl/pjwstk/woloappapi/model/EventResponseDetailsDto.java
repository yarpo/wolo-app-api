package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDetailsDto {

    @Schema(name = "Event ID", example = "1")
    private Long id;

    @Schema(name = "Event title", example = "Animal shelter assistance")
    private String name;

    @Schema(name = "Organisation ID", example = "1")
    private Long organisationId;

    @Schema(name = "Organisation", example = "Compassion Paws: Animal Shelter Support Initiative")
    private String organisationName;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

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
    private String description;

    private List<String> categories;

    private String imageUrl;

    private List<ShiftRequestDto> shifts;

    @Schema(name = "City name", example = "New York City")
    private String city;

}
