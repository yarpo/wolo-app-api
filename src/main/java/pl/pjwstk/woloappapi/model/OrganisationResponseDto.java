package pl.pjwstk.woloappapi.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganisationResponseDto {

    private String name;

    private String descriptionPL;

    private String descriptionEN;

    private String descriptionUA;

    private String descriptionRU;

    private String email;

    private String phoneNumber;

    private String street;

    private String homeNum;

    private String logoUrl;
}
