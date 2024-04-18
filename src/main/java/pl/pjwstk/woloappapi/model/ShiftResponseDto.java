package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class ShiftResponseDto {

    @Schema(example = "1")
    private Long shiftId;

    @Schema(example = "1")
    private Long eventId;

    private String eventName;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate date;

    private int capacity;

    private boolean isLeaderRequired;

    private int requiredMinAge;

    private int registeredUsers;

    private String district;

    private String street;

    private String homeNum;

    private String shiftDirections;
}
