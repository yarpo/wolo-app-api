package pl.pjwstk.woloappapi.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponseDetailsDto {

    private Long id;

    private String namePL;

    private String nameEN;

    private String nameUA;

    private String nameRU;

    private Long organisationId;

    private String organisationName;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

    private LocalDate date;

    private String descriptionPL;

    private String descriptionEN;

    private String descriptionUA;

    private String descriptionRU;

    private List<String> categories;

    private String imageUrl;

    private String alt;

    private List<ShiftResponseDto> shifts;

    private String city;

}
