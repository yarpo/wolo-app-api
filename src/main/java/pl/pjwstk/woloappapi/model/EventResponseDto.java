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

    private Long id;

    private String name;

    private String organisation;

    private boolean isPeselVerificationRequired;

    private String street;

    private String homeNum;

    private String district;

    private List<CategoryDto> categories;

    private String city;

    private String imageUrl;

    private List<ShiftDto> shifts;
}
