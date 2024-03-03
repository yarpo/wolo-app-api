package pl.pjwstk.woloappapi.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @Schema(name = "User ID", example = "1")
    private Long id;

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

    private boolean isPeselVerified;

    private boolean isAgreementSigned;

    private boolean isAdult;

    @NotNull(message = "Roles are required")
    @Size(min = 1, message = "At least one role is required")
    @Valid
    private List<String> roles;

}