package pl.pjwstk.woloappapi.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictResponseDto {

    private Long id;

    private String name;

    private String cityName;
}
