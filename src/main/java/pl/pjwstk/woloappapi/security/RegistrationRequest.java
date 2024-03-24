package pl.pjwstk.woloappapi.security;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotNull
    @NotBlank(message = "Firstname is required")
    @Size(max = 50, message = "Firstname cannot exceed 50 characters")
    private String firstname;

    @NotNull
    @NotBlank(message = "Surname is required")
    @Size(max = 50, message = "Surname cannot exceed 50 characters")
    private String lastname;

    @NotNull
    @NotBlank(message = "Email is required")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull
    @Pattern(regexp = "[0-9]{9}", message = "Phone number should consist of 9 digits")
    @Size(min = 9, max = 9, message = "Phone number must be 9 digits")
    private String phoneNumber;

    private boolean isAdult;

    @NotNull
    @NotBlank(message = "Password is required")
    @Size(max = 255, message = "Password cannot exceed 255 characters")
    private String password;
}
