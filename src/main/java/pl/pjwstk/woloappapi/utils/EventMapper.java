package pl.pjwstk.woloappapi.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.service.DistrictService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final DistrictService districtService;
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
        List<ShiftRequestDto> shifts = mapShiftListToShiftDtoList(event.getShifts());
        eventResponseDto.setShifts(shifts);
        List<String> categories = event.getCategories().stream()
                        .map(cte -> cte.getCategory().getName()).toList();
        eventResponseDto.setCategories(categories);



        return eventResponseDto;
    }

    private List<ShiftRequestDto> mapShiftListToShiftDtoList(List<Shift> shifts) {
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

    public ShiftRequestDto mapShiftToShiftDto(Shift shift) {
        ShiftRequestDto shiftDto = new ShiftRequestDto();
        shiftDto.setId(shift.getId());
        shiftDto.setStartTime(shift.getStartTime());
        shiftDto.setEndTime(shift.getEndTime());
        shiftDto.setDate(shift.getDate());
        shiftDto.setCapacity(shift.getCapacity());
        shiftDto.setIsLeaderRequired(shift.isLeaderRequired());
        shiftDto.setRequiredMinAge(shift.getRequiredMinAge());
        shiftDto.setShiftDirections(shift.getShiftDirections());
        shiftDto.setStreet(shift.getAddress().getStreet());
        shiftDto.setHomeNum(shift.getAddress().getHomeNum());
        shiftDto.setDistrictId(shift.getAddress().getDistrict().getId());
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
        List<ShiftRequestDto> shifts = mapShiftListToShiftDtoList(event.getShifts());
        eventResponseDto.setShifts(shifts);
        eventResponseDto.setCity(event.getCity().getName());

        return eventResponseDto;
    }

    public Event.EventBuilder toEvent(EventRequestDto dtoEvent){
        return Event.builder()
                .name(dtoEvent.getName())
                .description(dtoEvent.getDescription())
                .isPeselVerificationRequired(dtoEvent.isPeselVerificationRequired())
                .isAgreementNeeded(dtoEvent.isAgreementNeeded())
                .imageUrl(dtoEvent.getImageUrl());
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
