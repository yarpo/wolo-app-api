package pl.pjwstk.woloappapi.model;

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

    private String name;

    private Long organisationId;

    private String organisationName;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

    private String description;

    private List<Long> categories;

    private String street;

    private String addressDescription;

    private String homeNum;

    private String district;

    private String imageUrl;

    private List<ShiftDto> shifts;
}
