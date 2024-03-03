package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotNull;
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

    private Long id;

    @NotNull
    @TimeOrder
    private LocalTime startTime;

    @NotNull
    @TimeOrder
    private LocalTime endTime;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer capacity;

    private Integer signedUp;

    private Boolean isLeaderRequired;

    private Integer requiredMinAge;

    private String shiftDirections;
}
