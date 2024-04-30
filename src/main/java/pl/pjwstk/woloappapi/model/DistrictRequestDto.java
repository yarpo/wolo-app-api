package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictRequestDto {

    private Long id;

    @NotNull
    @NotBlank(message = "District name is required")
    @Size(max = 50, message = "District name cannot exceed 50 characters")
    private String name;

    @NotNull(message = "City id is required")
    private Long cityId;
}
