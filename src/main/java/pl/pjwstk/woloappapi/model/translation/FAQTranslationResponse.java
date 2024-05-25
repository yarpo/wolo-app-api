package pl.pjwstk.woloappapi.model.translation;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQTranslationResponse {

    private String questionPL;

    private String answerPL;

    private String questionEN;

    private String answerEN;

    private String questionUA;

    private String answerUA;

    private String questionRU;

    private String answerRU;
}
