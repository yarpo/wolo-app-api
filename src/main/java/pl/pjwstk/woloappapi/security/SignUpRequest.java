package pl.pjwstk.woloappapi.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpRequest {
    //TODO expand to cover all the fields in frontend
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    private String phoneNumber;
}
