package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventTranslationResponsDto {
    private String titlePL;
    private String descriptionPL;
    private String addressDescriptionPL;
    private String titleEN;
    private String descriptionEN;
    private String addressDescriptionEN;
    private String titleUA;
    private String descriptionUA;
    private String addressDescriptionUA;
    private String titleRU;
    private String descriptionRU;
    private String addressDescriptionRU;
}
