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
public class EventRequestEditDto {

    private String name;

    private String description;

    private Long organisationId;

    private List<Long> categories;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

    private String street;

    private String addressDescription;

    private String homeNum;

    private Long districtId;

    private List<ShiftDto> shifts;
}
