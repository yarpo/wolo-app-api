package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ShiftResponseDto {

    @Schema(name = "Shift ID", example = "1")
    private Long shiftId;

    @Schema(name = "Event ID", example = "1")
    private Long eventId;

    @NotNull
    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name cannot exceed 250 characters")
    @Schema(name = "Event title", example = "Animal shelter assistance")
    private String name;

    @NotNull
    @TimeOrder
    private LocalTime startTime;

    @NotNull
    @TimeOrder
    private LocalTime endTime;

    @NotNull
    private LocalDate date;

    @NotNull
    @Schema(name = "City name", example = "New York City")
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    private String city;

    @NotNull
    @NotBlank(message = "Street is required")
    @Size(max = 50, message = "Street cannot exceed 50 characters")
    private String street;

    @NotNull
    @NotBlank(message = "Home number is required")
    @Size(max = 10, message = "Home number cannot exceed 10 characters")
    private String homeNum;

}
