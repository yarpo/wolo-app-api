package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long roleId;

    private boolean isPeselVerified;

    private boolean isAgreementSigned;

    private boolean isAdult;
    private String password;
}
