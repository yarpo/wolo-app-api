package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String firstname;

    @NotBlank(message = "Surname is required")
    @Size(max = 50, message = "Surname cannot exceed 50 characters")
    private String lastname;

    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "[0-9]{9}", message = "Phone number should consist of 9 digits")
    private String phoneNumber;

    @NotBlank(message = "role is required")
    private RoleDto roleDto;

    private boolean isPeselVerified;

    private boolean isAgreementSigned;

    private boolean isAdult;
    @NotBlank(message = "password is required")
    private String password_hash;
    @NotBlank(message = "salt is required")
    private String salt;

}
