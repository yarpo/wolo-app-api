package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "Shift ID", example = "1")
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

    private String street;

    private String homeNum;

    private String district;
}
