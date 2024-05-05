package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityResponseAdminDto {

    private Long id;

    @NotNull
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    private String name;

    private List<String> districts;

    private boolean isOld;
}
