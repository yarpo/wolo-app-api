package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventAIRequest {
    private Long id;
    private String organisation;
    private String district;
    private List<String> categories;
}
