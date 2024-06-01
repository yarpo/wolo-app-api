package pl.pjwstk.woloappapi.model.translation;

import java.util.List;

public class EventTranslationRequest {

    private String name;

    private String description;

    private List<String> shiftDirections;

    private String language;

    public EventTranslationRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getShiftDirections() {
        return shiftDirections;
    }

    public void setShiftDirections(List<String> shiftDirections) {
        this.shiftDirections = shiftDirections;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
