package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportEditRequestDto {
    private Long id;

    @NotEmpty
    private String reportPL;

    @NotEmpty
    private String reportEN;

    @NotEmpty
    private String reportUA;

    @NotEmpty
    private String reportRU;

    private boolean published;

    @NotNull
    private Long event;
}
