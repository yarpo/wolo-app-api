package pl.pjwstk.woloappapi.model;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
