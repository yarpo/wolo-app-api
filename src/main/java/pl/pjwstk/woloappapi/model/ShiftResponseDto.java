package pl.pjwstk.woloappapi.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShiftResponseDto {

    private Long shiftId;

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
