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
public class UserResponseDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private boolean isPeselVerified;
    private boolean isAgreementSigned;
    private boolean isAdult;
    private List<String> roles;

}