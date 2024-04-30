package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShiftTranslation {
    private String addressDescriptionPl;
    private String addressDescriptionEN;
    private String addressDescriptionUA;
    private String addressDescriptionRU;
}
