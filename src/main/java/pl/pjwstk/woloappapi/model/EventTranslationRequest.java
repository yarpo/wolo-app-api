package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventTranslationRequest {

    private String name;

    private String description;

    private List<String> shiftDirections;

    private String language;
}
