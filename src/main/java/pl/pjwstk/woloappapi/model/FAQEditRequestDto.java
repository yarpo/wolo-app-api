package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQEditRequestDto {

    private Long id;

    @NotNull
    @NotBlank(message = "Question is required")
    private String questionPL;

    @NotNull
    @NotBlank(message = "Answer is required")
    private String answerPL;

    @NotNull
    @NotBlank(message = "Question is required")
    private String questionEN;

    @NotNull
    @NotBlank(message = "Answer is required")
    private String answerEN;

    @NotNull
    @NotBlank(message = "Question is required")
    private String questionUA;

    @NotNull
    @NotBlank(message = "Answer is required")
    private String answerUA;

    @NotNull
    @NotBlank(message = "Question is required")
    private String questionRU;

    @NotNull
    @NotBlank(message = "Answer is required")
    private String answerRU;
}
