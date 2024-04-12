package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShortResponse {

    @Schema(name = "firstname", example = "Jhon")
    private String firstName;

    @Schema(name = "lastname", example = "Doe")
    private String lastName;

    @Schema(name = "email", example = "example@mail.com")
    private String email;

    private String phoneNumber;

}
