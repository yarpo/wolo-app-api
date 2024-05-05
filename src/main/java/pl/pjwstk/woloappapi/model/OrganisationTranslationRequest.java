package pl.pjwstk.woloappapi.model;

public class OrganisationTranslationRequest {
    private String language;
    private String description;

    public OrganisationTranslationRequest() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
