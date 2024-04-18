package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDto {

    @Schema(example = "1")
    private Long id;

    @NotNull
    @Schema(example = "DownTown")
    @NotBlank(message = "District name is required")
    @Size(max = 50, message = "District name cannot exceed 50 characters")
    private String name;

    @NotNull
    @Schema(example = "New York City")
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    private String cityName;
}
