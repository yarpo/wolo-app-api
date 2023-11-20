package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRequestEvent {

    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name cannot exceed 250 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private Long organisationId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

    @NotBlank(message = "Street is required")
    @Size(max = 200, message = "Street cannot exceed 200 characters")
    private String street;

    @NotBlank(message = "Home number is required")
    @Size(max = 20, message = "Home number cannot exceed 20 characters")
    private String homeNum;

    @NotNull(message = "District ID is required")
    private Long districtId;

    private String addressDescription;


    private LocalTime startTime;


    private LocalTime endTime;

    @Future(message = "Date must be in the future")
    private LocalDate date;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    private boolean isLeaderRequired;

    @Min(value = 0, message = "Minimum age cannot be negative")
    private int requiredMinAge;
}
