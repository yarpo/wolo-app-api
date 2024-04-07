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
public class EventResponseDto {

    @Schema(name = "Organisation ID", example = "1")
    private Long id;

    @Schema(name = "Organisation", example = "Compassion Paws: Animal Shelter Support Initiative")
    private String name;

    private String organisation;

    private boolean isPeselVerificationRequired;

    private List<String> categories;

    @Schema(name = "City name", example = "New York City")
    private String city;

    private String imageUrl;

    private List<ShiftDto> shifts;
}
