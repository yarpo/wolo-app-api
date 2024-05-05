package pl.pjwstk.woloappapi.model.admin;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictResponseAdminDto {

    private Long id;

    private String name;

    private String cityName;

    private boolean isOld;
}
