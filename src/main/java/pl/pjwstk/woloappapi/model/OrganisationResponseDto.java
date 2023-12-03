package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationResponseDto {

    private String name;

    private String description;

    private String email;

    private String phoneNumber;

    private String street;

    private String homeNum;

    private String addressDescription;

    private String logoUrl;

}
