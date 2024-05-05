package pl.pjwstk.woloappapi.model.translation;

public class ReportTranslationRequest {
    private String language;
    private String report;

    public ReportTranslationRequest() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
