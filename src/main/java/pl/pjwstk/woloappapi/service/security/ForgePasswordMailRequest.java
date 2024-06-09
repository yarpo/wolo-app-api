package pl.pjwstk.woloappapi.service.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgePasswordMailRequest {
    private String mail;
}
