package pl.pjwstk.woloappapi.model;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
}
