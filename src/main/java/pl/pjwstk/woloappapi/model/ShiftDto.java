package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjwstk.woloappapi.annotations.TimeOrder;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDto {

    @TimeOrder
    private LocalTime startTime;

    @TimeOrder
    private LocalTime endTime;

    private LocalDate date;

    private Integer capacity;

    private Integer signedUp;

    private Boolean isLeaderRequired;

    private Integer requiredMinAge;

}

