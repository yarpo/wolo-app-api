package pl.pjwstk.woloappapi.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponceDto {

    private Long id;

    private String reportPL;

    private String reportEN;

    private String reportUA;

    private String reportRU;

    private boolean published;

    private Long event;
}
