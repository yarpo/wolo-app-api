package pl.pjwstk.woloappapi.model.admin;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganisationResponseAdminDto {

    private Long id;

    private String name;

    private String descriptionPL;

    private String descriptionEN;

    private String descriptionUA;

    private String descriptionRU;

    private String email;

    private String phoneNumber;

    private String street;

    private String homeNum;

    private Long cityId;

    private Long districtId;

    private String logoUrl;

    private boolean isApproved;

}
