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

    private Long id;

    private String name;

    private Long organisationId;

    private String organisationName;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

    private String description;

    private List<String> categories;

    private String imageUrl;

    private List<ShiftRequestDto> shifts;

    private String city;

}
