package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequestDto {

    @NotNull
    @NotBlank(message = "First Name is required")
    @Size(max = 50, message = "First Name cannot exceed 50 characters")
    private String firstName;

    @NotNull
    @NotBlank(message = "Surname is required")
    @Size(max = 50, message = "Surname cannot exceed 50 characters")
    private String lastName;

    @NotNull
    @Pattern(regexp = "[0-9]{9}", message = "Phone number should consist of 9 digits")
    @Size(min = 9, max = 9, message = "Phone number must be 9 digits")
    private String phoneNumber;

    private boolean isAdult;
}
