package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FAQResponseDto {

    private Long id;

    private String questionPL;

    private String answerPL;

    private String questionEN;

    private String answerEN;

    private String questionUA;

    private String answerUA;

    private String questionRU;

    private String answerRU;

}
