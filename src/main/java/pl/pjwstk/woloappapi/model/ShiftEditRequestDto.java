package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftEditRequestDto {

    private Long id;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private Integer capacity;

    private Boolean isLeaderRequired;

    private Integer requiredMinAge;

    private String shiftDirectionsPL;
    private String shiftDirectionsEN;
    private String shiftDirectionsUA;
    private String shiftDirectionsRU;

    @NotNull
    @NotBlank(message = "Street is required")
    @Size(max = 50, message = "Street cannot exceed 50 characters")
    private String street;

    @NotNull
    @NotBlank(message = "Home number is required")
    @Size(max = 10, message = "Home number cannot exceed 10 characters")
    private String homeNum;

    @NotNull(message = "District must be chosen")
    private Long districtId;
}
