package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {

    private Long id;

    @NotEmpty
    private String report;

    private boolean published;

    @NotNull
    private Long event;
}
