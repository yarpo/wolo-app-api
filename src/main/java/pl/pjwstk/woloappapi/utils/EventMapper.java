package pl.pjwstk.woloappapi.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {
    public Shift.ShiftBuilder toShift (ShiftDto shiftDto){
        return Shift.builder()
                .startTime(shiftDto.getStartTime())
                .endTime(shiftDto.getEndTime())
                .date(shiftDto.getDate())
                .isLeaderRequired(shiftDto.getIsLeaderRequired())
                .capacity(shiftDto.getCapacity())
                .requiredMinAge(shiftDto.getRequiredMinAge())
                .shiftDirections(shiftDto.getShiftDirections());
    }
    public Address.AddressBuilder toAddress(EventRequestDto dtoEvent) {
        return Address.builder()
                .street(dtoEvent.getStreet())
                .homeNum(dtoEvent.getHomeNum());
    }

    public EventResponseDto toEventResponseDto(Event event) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setName(event.getName());
        eventResponseDto.setOrganisation(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setHomeNum(address.getHomeNum());
        eventResponseDto.setDistrict(address.getDistrict().getName());
        eventResponseDto.setCity(address.getDistrict().getCity());
        eventResponseDto.setImageUrl(event.getImageUrl());
        List<ShiftDto> shifts =
                event.getAddressToEvents().stream()
                        .flatMap(
                                addressToEvent ->
                                        mapShiftListToShiftDtoList(addressToEvent.getShifts())
                                                .stream())
                        .collect(Collectors.toList());
        eventResponseDto.setShifts(shifts);
        List<String> categories = event.getCategories().stream()
                        .map(cte -> cte.getCategory().getName()).toList();
        eventResponseDto.setCategories(categories);



        return eventResponseDto;
    }

    private List<ShiftDto> mapShiftListToShiftDtoList(List<Shift> shifts) {
        return shifts.stream()
                .map(this::mapShiftToShiftDto)
                .collect(Collectors.toList());
    }

    public ShiftDto mapShiftToShiftDto(Shift shift) {
        ShiftDto shiftDto = new ShiftDto();
        shiftDto.setId(shift.getId());
        shiftDto.setStartTime(shift.getStartTime());
        shiftDto.setEndTime(shift.getEndTime());
        shiftDto.setDate(shift.getDate());
        shiftDto.setSignedUp(shift.getRegisteredUsers());
        shiftDto.setCapacity(shift.getCapacity());
        shiftDto.setIsLeaderRequired(shift.isLeaderRequired());
        shiftDto.setRequiredMinAge(shift.getRequiredMinAge());
        shiftDto.setShiftDirections(shift.getShiftDirections());
        return shiftDto;
    }

    public List<ShiftResponseDto> toShiftResponseDto(Event event){
        List<ShiftResponseDto> shiftResponseDtos = new ArrayList<>();
        List<AddressToEvent> addressToEvents = event.getAddressToEvents();
        for (AddressToEvent addressToEvent : addressToEvents) {
            Address address = addressToEvent.getAddress();
            String street = address.getStreet();
            String homeNum = address.getHomeNum();
            String city = address.getDistrict().getCity();

            List<Shift> shifts = addressToEvent.getShifts();
            for (Shift shift : shifts) {
                ShiftResponseDto shiftResponseDto = new ShiftResponseDto();
                shiftResponseDto.setShiftId(shift.getId());
                shiftResponseDto.setEventId(event.getId());
                shiftResponseDto.setName(event.getName());
                shiftResponseDto.setStartTime(shift.getStartTime());
                shiftResponseDto.setEndTime(shift.getEndTime());
                shiftResponseDto.setDate(shift.getDate());
                shiftResponseDto.setStreet(street);
                shiftResponseDto.setHomeNum(homeNum);
                shiftResponseDto.setCity(city);
                shiftResponseDtos.add(shiftResponseDto);
            }
        }
        return shiftResponseDtos;
    }

    public EventResponseDetailsDto toEventResponseDetailsDto(Event event) {
        EventResponseDetailsDto eventResponseDto = new EventResponseDetailsDto();
        eventResponseDto.setName(event.getName());
        eventResponseDto.setOrganisationId(event.getOrganisation().getId());
        eventResponseDto.setOrganisationName(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        eventResponseDto.setDescription(event.getDescription());
        eventResponseDto.setCategories(event.getCategories().stream()
                        .map(cte ->cte.getCategory().getName()).toList());
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setHomeNum(address.getHomeNum());
        eventResponseDto.setDistrict(address.getDistrict().getName());
        eventResponseDto.setImageUrl(event.getImageUrl());
        List<ShiftDto> shifts =
                event.getAddressToEvents().stream()
                        .flatMap(addressToEvent ->
                                mapShiftListToShiftDtoList(addressToEvent.getShifts())
                                        .stream()).toList();

        eventResponseDto.setShifts(shifts);

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

    public List<Shift> toShifts(List<ShiftDto> shiftDtos) {
        return shiftDtos.stream()
                .map(s -> this.toShift(s).build())
                .collect(Collectors.toList());
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
