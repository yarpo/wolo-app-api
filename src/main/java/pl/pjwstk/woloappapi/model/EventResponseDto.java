package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {

    private String name;

    private Long organisation_id;

    private boolean isPeselVerificationRequired;

    private String street;

    private String homeNum;

    private Long districtId;

    private String addressDescription;

    private String imageUrl;

    private List<ShiftDto> shifts;

}