package pl.pjwstk.woloappapi.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private List<ShiftResponseDto> shifts;

    private String city;

}
