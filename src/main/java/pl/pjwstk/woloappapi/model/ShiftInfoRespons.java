package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShiftInfoRespons {
    @Schema(name = "Shift ID", example = "1")
    private Long id;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate date;

    private String shiftDirections;

    private Long eventId;

    private String eventName;

    private String address;
}
