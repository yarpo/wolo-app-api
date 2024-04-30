package pl.pjwstk.woloappapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganisationResponseDto {

    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private String email;

    private String phoneNumber;

    private String street;

    private String homeNum;

    @Size(max = 255, message = "Logo Url cannot exceed 255 characters")
    private String logoUrl;
}
