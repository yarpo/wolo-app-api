package pl.pjwstk.woloappapi.model;

import lombok.*;

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
    private RoleDto roleDto;
    private boolean isPeselVerified;
    private boolean isAgreementSigned;
    private boolean isAdult;

}