package pl.pjwstk.woloappapi.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.service.CityService;
import pl.pjwstk.woloappapi.service.DistrictService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final DistrictService districtService;
    private final CityService cityService;
    public Shift.ShiftBuilder toShift (ShiftRequestDto shiftDto){
        var address = toAddress(shiftDto)
                .district(districtService.getDistrictById(shiftDto.getDistrictId()))
                .build();
        return Shift.builder()
                .startTime(shiftDto.getStartTime())
                .endTime(shiftDto.getEndTime())
                .date(shiftDto.getDate())
                .address(address)
                .isLeaderRequired(shiftDto.getIsLeaderRequired())
                .capacity(shiftDto.getCapacity())
                .requiredMinAge(shiftDto.getRequiredMinAge())
                .shiftDirections(shiftDto.getShiftDirections());
    }
    public Address.AddressBuilder toAddress(ShiftRequestDto shiftDto) {
        return Address.builder()
                .street(shiftDto.getStreet())
                .homeNum(shiftDto.getHomeNum());
    }

    public EventResponseDto toEventResponseDto(Event event) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setName(event.getName());
        eventResponseDto.setOrganisation(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        eventResponseDto.setCity(event.getCity().getName());
        eventResponseDto.setImageUrl(event.getImageUrl());
        var shifts = mapShiftListToShiftDtoList(event.getShifts());
        eventResponseDto.setShifts(shifts);
        List<String> categories = event.getCategories().stream()
                        .map(cte -> cte.getCategory().getName()).toList();
        eventResponseDto.setCategories(categories);
        return eventResponseDto;
    }

    private List<ShiftResponseDto> mapShiftListToShiftDtoList(List<Shift> shifts) {
        return shifts.stream()
                .map(this::mapShiftToShiftDto)
                .collect(Collectors.toList());
    }

    public ShiftInfoRespons toShiftInfoRespons(Shift shift){
        return ShiftInfoRespons.builder()
                .id(shift.getId())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .date(shift.getDate())
                .shiftDirections(shift.getShiftDirections())
                .eventId(shift.getEvent().getId())
                .eventName(shift.getEvent().getName())
                .address(shift.getAddress().getStreet()
                        + " "
                        + shift.getAddress().getHomeNum())
                .build();
    }

    public ShiftResponseDto mapShiftToShiftDto(Shift shift) {
        var shiftDto = new ShiftResponseDto();
        shiftDto.setShiftId(shift.getId());
        shiftDto.setEventId(shift.getEvent().getId());
        shiftDto.setEventName(shift.getEvent().getName());
        shiftDto.setStartTime(shift.getStartTime());
        shiftDto.setEndTime(shift.getEndTime());
        shiftDto.setDate(shift.getDate());
        shiftDto.setCapacity(shift.getCapacity());
        shiftDto.setLeaderRequired(shift.isLeaderRequired());
        shiftDto.setRequiredMinAge(shift.getRequiredMinAge());
        shiftDto.setRegisteredUsers(shift.getRegisteredUsers());
        shiftDto.setDistrict(shift.getAddress().getDistrict().getName());
        shiftDto.setStreet(shift.getAddress().getStreet());
        shiftDto.setHomeNum(shift.getAddress().getHomeNum());
        shiftDto.setShiftDirections(shift.getShiftDirections());
        return shiftDto;
    }

    public EventResponseDetailsDto toEventResponseDetailsDto(Event event) {
        EventResponseDetailsDto eventResponseDto = new EventResponseDetailsDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setName(event.getName());
        eventResponseDto.setOrganisationId(event.getOrganisation().getId());
        eventResponseDto.setOrganisationName(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        eventResponseDto.setDescription(event.getDescription());
        eventResponseDto.setCategories(event.getCategories().stream()
                        .map(cte ->cte.getCategory().getName()).toList());
        eventResponseDto.setImageUrl(event.getImageUrl());
        var shifts = mapShiftListToShiftDtoList(event.getShifts());
        eventResponseDto.setShifts(shifts);
        eventResponseDto.setCity(event.getCity().getName());

        return eventResponseDto;
    }

    public ShiftResponseDto toShiftResponseDto(Shift shift) {
        return ShiftResponseDto.builder()
                .shiftId(shift.getId())
                .eventId(shift.getEvent().getId())
                .eventName(shift.getEvent().getName())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .date(shift.getDate())
                .capacity(shift.getCapacity())
                .isLeaderRequired(shift.isLeaderRequired())
                .requiredMinAge(shift.getRequiredMinAge())
                .registeredUsers(shift.getRegisteredUsers())
                .district(shift.getAddress().getDistrict().getName())
                .street(shift.getAddress().getStreet())
                .homeNum(shift.getAddress().getHomeNum())
                .shiftDirections(shift.getShiftDirections() != null ? shift.getShiftDirections() : "")
                .build();
    }

    public Event.EventBuilder toEvent(EventRequestDto dtoEvent){
        return Event.builder()
                .name(dtoEvent.getName())
                .description(dtoEvent.getDescription())
                .isPeselVerificationRequired(dtoEvent.isPeselVerificationRequired())
                .isAgreementNeeded(dtoEvent.isAgreementNeeded())
                .imageUrl(dtoEvent.getImageUrl())
                .city(cityService.getCityById(dtoEvent.getCityId()));
    }

    public Report.ReportBuilder toReport(ReportDto reportDto) {
        return Report.builder()
                .report(reportDto.getReport())
                .published(reportDto.isPublished());
    }

    public ReportDto toReportDto(Report report) {
        return ReportDto.builder()
                .event(report.getEvent().getId())
                .published(report.isPublished())
                .report(report.getReport())
                .build();
    }
}
