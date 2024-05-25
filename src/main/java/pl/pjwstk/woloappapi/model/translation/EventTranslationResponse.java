package pl.pjwstk.woloappapi.model.translation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
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

}


