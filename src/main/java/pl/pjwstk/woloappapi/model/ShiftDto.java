package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDto {

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate date;

    private int capacity;

    private boolean isLeaderRequired;

    private int requiredMinAge;

}

