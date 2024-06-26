package pl.pjwstk.woloappapi.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponseDto {

    private Long id;

    private String namePL;

    private String nameEN;

    private String nameUA;

    private String nameRU;

    private String organisation;

    private Boolean isPeselVerificationRequired;

    private Boolean isAgreementNeeded;

    private List<String> categories;

    private String city;

    private LocalDate date;

    private String imageUrl;

    private List<ShiftResponseDto> shifts;
}
