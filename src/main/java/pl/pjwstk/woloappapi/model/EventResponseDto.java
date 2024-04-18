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

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Animal shelter assistance")
    private String name;

    @Schema(example = "Compassion Paws: Animal Shelter Support Initiative")
    private String organisation;

    private boolean isPeselVerificationRequired;

    private List<String> categories;

    @Schema(example = "New York City")
    private String city;

    private String imageUrl;

    private List<ShiftRequestDto> shifts;
}
