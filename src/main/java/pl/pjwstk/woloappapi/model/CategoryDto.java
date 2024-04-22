package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CategoryDto {

    private Long id;

    @NotNull
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

}
