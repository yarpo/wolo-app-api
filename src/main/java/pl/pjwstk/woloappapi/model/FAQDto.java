package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FAQDto {

    private Long id;

    @NotNull
    @NotBlank(message = "Question is required")
    private String question;

    @NotNull
    @NotBlank(message = "Answer is required")
    private String answer;

}
