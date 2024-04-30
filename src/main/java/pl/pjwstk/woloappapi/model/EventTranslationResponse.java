package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventTranslationResponse {
    private String namePL;
    private String nameEN;
    private String nameUA;
    private String nameRU;

    private String descriptionPL;
    private String descriptionEN;
    private String descriptionUA;
    private String descriptionRU;

    private List<ShiftTranslation> shiftTranslations;

    private String alt;
}
