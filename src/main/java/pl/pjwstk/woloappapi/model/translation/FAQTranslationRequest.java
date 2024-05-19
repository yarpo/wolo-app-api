package pl.pjwstk.woloappapi.model.translation;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQTranslationRequest {

    private String language;

    @NotEmpty
    private String question;

    @NotEmpty
    private String answer;
}
