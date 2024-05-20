package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQRequestDto {

    @NotEmpty
    private String question;

    @NotEmpty
    private String answer;

}
