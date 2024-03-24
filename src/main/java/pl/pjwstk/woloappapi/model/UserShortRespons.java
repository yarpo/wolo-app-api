package pl.pjwstk.woloappapi.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShortRespons {

    private String firstname;

    private String lastname;

    private String email;

    private String phoneNumber;

}
