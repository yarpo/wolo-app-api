package pl.pjwstk.woloappapi.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShiftInfoRespons {

    private Long id;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate date;

    private String shiftDirectionsPL;

    private String shiftDirectionsEN;

    private String shiftDirectionsUA;

    private String shiftDirectionsRU;

    private Long eventId;

    private String eventNamePL;

    private String eventNameEN;

    private String eventNameUA;

    private String eventNameRU;

    private String address;
}
