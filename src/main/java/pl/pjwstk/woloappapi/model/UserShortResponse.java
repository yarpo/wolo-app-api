package pl.pjwstk.woloappapi.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShortResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

}
