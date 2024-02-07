package pl.pjwstk.woloappapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventTranslateRequestDto {

    private String name_pl;

    private String name_en;

    private String name_ua;

    private String name_ru;

    private String description_pl;
    private String description_en;
    private String description_ua;
    private String description_ru;

    private Long organisationId;

    private List<Long> categories;

    private boolean isPeselVerificationRequired;

    private boolean isAgreementNeeded;

    private String street;

    private String homeNum;

    private Long districtId;

    private String addressDescription_pl;
    private String addressDescription_en;
    private String addressDescription_ua;
    private String addressDescription_ru;

    private String imageUrl;

    private List<ShiftDto> shifts;


}
