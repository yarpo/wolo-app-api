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
public class EventResponseDto {

    private String name;

    private Long organisationId;

    private boolean isPeselVerificationRequired;

    private String street;

    private String homeNum;

    private Long districtId;

    private String imageUrl;

    private List<ShiftDto> shifts;

}