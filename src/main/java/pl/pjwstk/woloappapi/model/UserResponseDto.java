package pl.pjwstk.woloappapi.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private boolean isPeselVerified;

    private boolean isAgreementSigned;

    private boolean isAdult;

    private List<String> roles;

    private Long organisationId;

    private String organisationName;

    private List<ShiftInfoRespons> shifts;
}