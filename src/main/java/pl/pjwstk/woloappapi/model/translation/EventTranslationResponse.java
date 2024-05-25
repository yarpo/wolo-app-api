package pl.pjwstk.woloappapi.model.translation;

import pl.pjwstk.woloappapi.model.ShiftTranslation;

import java.util.List;

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

    public EventTranslationResponse() {
    }

    public String getNamePL() {
        return namePL;
    }

    public void setNamePL(String namePL) {
        this.namePL = namePL;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNameUA() {
        return nameUA;
    }

    public void setNameUA(String nameUA) {
        this.nameUA = nameUA;
    }

    public String getNameRU() {
        return nameRU;
    }

    public void setNameRU(String nameRU) {
        this.nameRU = nameRU;
    }

    public String getDescriptionPL() {
        return descriptionPL;
    }

    public void setDescriptionPL(String descriptionPL) {
        this.descriptionPL = descriptionPL;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionUA() {
        return descriptionUA;
    }

    public void setDescriptionUA(String descriptionUA) {
        this.descriptionUA = descriptionUA;
    }

    public String getDescriptionRU() {
        return descriptionRU;
    }

    public void setDescriptionRU(String descriptionRU) {
        this.descriptionRU = descriptionRU;
    }

    public List<ShiftTranslation> getShiftTranslations() {
        return shiftTranslations;
    }

    public void setShiftTranslations(List<ShiftTranslation> shiftTranslations) {
        this.shiftTranslations = shiftTranslations;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
