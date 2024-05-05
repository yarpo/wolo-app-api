package pl.pjwstk.woloappapi.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.model.translation.EventTranslationRequest;
import pl.pjwstk.woloappapi.model.translation.EventTranslationResponse;
import pl.pjwstk.woloappapi.model.translation.ReportTranslationResponce;
import pl.pjwstk.woloappapi.service.CityService;
import pl.pjwstk.woloappapi.service.DistrictService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final DistrictService districtService;
    private final CityService cityService;

    public Shift.ShiftBuilder toShift (ShiftRequestDto shiftDto,
                                       EventRequestDto eventDto,
                                       EventTranslationResponse translate){
        var translation = translate.getShiftTranslations().get(eventDto.getShifts().indexOf(shiftDto));
        var address = toAddress(shiftDto)
                .district(districtService.getDistrictById(shiftDto.getDistrictId()))
                .build();
        return Shift.builder()
                .startTime(shiftDto.getStartTime())
                .endTime(shiftDto.getEndTime())
                .address(address)
                .isLeaderRequired(shiftDto.getIsLeaderRequired())
                .capacity(shiftDto.getCapacity())
                .requiredMinAge(shiftDto.getRequiredMinAge())
                .shiftDirectionsPL(translation.getAddressDescriptionPl())
                .shiftDirectionsEN(translation.getAddressDescriptionEN())
                .shiftDirectionsUA(translation.getAddressDescriptionUA())
                .shiftDirectionsRU(translation.getAddressDescriptionRU());
    }
    public Address.AddressBuilder toAddress(ShiftRequestDto shiftDto) {
        return Address.builder()
                .street(shiftDto.getStreet())
                .homeNum(shiftDto.getHomeNum());
    }

    public EventResponseDto toEventResponseDto(Event event) {
        var shifts = mapShiftListToShiftDtoList(event.getShifts());
        List<String> categories = event.getCategories().stream()
                .map(cte -> cte.getCategory().getName()).toList();
        return EventResponseDto.builder()
                .id(event.getId())
                .namePL(event.getNamePL())
                .nameEN(event.getNameEN())
                .nameUA(event.getNameUA())
                .nameRU(event.getNameRU())
                .organisation(event.getOrganisation().getName())
                .date(event.getDate())
                .isPeselVerificationRequired(event.isPeselVerificationRequired())
                .city(event.getCity().getName())
                .alt(event.getAlt())
                .imageUrl(event.getImageUrl())
                .shifts(shifts)
                .categories(categories)
                .build();
    }

    private List<ShiftResponseDto> mapShiftListToShiftDtoList(List<Shift> shifts) {
        return shifts.stream()
                .map(this::toShiftResponseDto)
                .collect(Collectors.toList());
    }

    public ShiftInfoRespons toShiftInfoRespons(Shift shift){
        return ShiftInfoRespons.builder()
                .id(shift.getId())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .shiftDirectionsPL(shift.getShiftDirectionsPL())
                .shiftDirectionsEN(shift.getShiftDirectionsEN())
                .shiftDirectionsUA(shift.getShiftDirectionsUA())
                .shiftDirectionsRU(shift.getShiftDirectionsRU())
                .eventId(shift.getEvent().getId())
                .eventNamePL(shift.getEvent().getNamePL())
                .eventNameEN(shift.getEvent().getNameEN())
                .eventNameUA(shift.getEvent().getNameUA())
                .eventNameRU(shift.getEvent().getNameRU())
                .address(shift.getAddress().getStreet()
                        + " "
                        + shift.getAddress().getHomeNum())
                .build();
    }

    public EventResponseDetailsDto toEventResponseDetailsDto(Event event) {
        var shifts = mapShiftListToShiftDtoList(event.getShifts());
        return EventResponseDetailsDto.builder()
                .id(event.getId())
                .namePL(event.getNamePL())
                .nameEN(event.getNameEN())
                .nameUA(event.getNameUA())
                .nameRU(event.getNameRU())
                .organisationId(event.getOrganisation().getId())
                .organisationName(event.getOrganisation().getName())
                .date(event.getDate())
                .isPeselVerificationRequired(event.isPeselVerificationRequired())
                .descriptionPL(event.getDescriptionPL())
                .descriptionEN(event.getDescriptionEN())
                .descriptionUA(event.getDescriptionUA())
                .descriptionRU(event.getDescriptionRU())
                .categories(event.getCategories().stream()
                        .map(cte ->cte.getCategory().getName()).toList())
                .imageUrl(event.getImageUrl())
                .shifts(shifts)
                .city(event.getCity().getName())
                .build();
    }

    public ShiftResponseDto toShiftResponseDto(Shift shift) {
        return ShiftResponseDto.builder()
                .shiftId(shift.getId())
                .eventId(shift.getEvent().getId())
                .eventNamePL(shift.getEvent().getNamePL())
                .eventNameEN(shift.getEvent().getNameEN())
                .eventNameUA(shift.getEvent().getNameUA())
                .eventNameRU(shift.getEvent().getNameRU())
                .date(shift.getEvent().getDate())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .capacity(shift.getCapacity())
                .isLeaderRequired(shift.isLeaderRequired())
                .requiredMinAge(shift.getRequiredMinAge())
                .registeredUsers(shift.getRegisteredUsers())
                .district(shift.getAddress().getDistrict().getName())
                .street(shift.getAddress().getStreet())
                .homeNum(shift.getAddress().getHomeNum())
                .shiftDirectionsPL(shift.getShiftDirectionsPL())
                .shiftDirectionsEN(shift.getShiftDirectionsEN())
                .shiftDirectionsUA(shift.getShiftDirectionsUA())
                .shiftDirectionsRU(shift.getShiftDirectionsRU())
                .build();
    }

    public Event.EventBuilder toEvent(EventRequestDto dtoEvent, EventTranslationResponse translation){
        return Event.builder()
                .namePL(translation.getNamePL())
                .nameEN(translation.getNameEN())
                .nameUA(translation.getNameUA())
                .nameRU(translation.getNameRU())
                .date(dtoEvent.getDate())
                .descriptionPL(translation.getDescriptionPL())
                .descriptionEN(translation.getDescriptionEN())
                .descriptionUA(translation.getDescriptionUA())
                .descriptionRU(translation.getDescriptionRU())
                .isPeselVerificationRequired(dtoEvent.isPeselVerificationRequired())
                .isAgreementNeeded(dtoEvent.isAgreementNeeded())
                .imageUrl(dtoEvent.getImageUrl())
                .alt(translation.getAlt())
                .city(cityService.getCityById(dtoEvent.getCityId()));
    }

    public Report.ReportBuilder toReport(ReportRequestDto reportDto, ReportTranslationResponce translation) {
        return Report.builder()
                .reportPL(translation.getReportPL())
                .reportEN(translation.getReportEN())
                .reportUA(translation.getReportUA())
                .reportRU(translation.getReportRU())
                .published(reportDto.isPublished());
    }

    public ReportResponceDto toReportResponceDto(Report report) {
        return ReportResponceDto.builder()
                .event(report.getEvent().getId())
                .published(report.isPublished())
                .reportPL(report.getReportPL())
                .reportEN(report.getReportEN())
                .reportUA(report.getReportUA())
                .reportRU(report.getReportRU())
                .build();
    }

    public EventTranslationRequest toEventTranslationDto(EventRequestDto dtoEvent, String language) {
        var directions = dtoEvent.getShifts()
                .stream()
                .map(ShiftRequestDto::getShiftDirections)
                .toList();
        var translation = new EventTranslationRequest();
        translation.setName(dtoEvent.getName());
        translation.setDescription(dtoEvent.getDescription());
        translation.setImageUrl(dtoEvent.getImageUrl());
        translation.setShiftDirections(directions);
        translation.setLanguage(language);

        return translation;

    }
}
