package pl.pjwstk.woloappapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "Category id", example = "1")
    private Long id;

    @Schema(name = "Category title", example = "Health")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

}
