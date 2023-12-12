package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventMapper {
    default Shift toShift(ShiftDto shiftDto) {
        Shift shift = new Shift();
        shift.setStartTime(shiftDto.getStartTime());
        shift.setEndTime(shiftDto.getEndTime());
        shift.setDate(shiftDto.getDate());
        shift.setLeaderRequired(shiftDto.getIsLeaderRequired());
        shift.setCapacity(shiftDto.getCapacity());
        shift.setRequiredMinAge(shiftDto.getRequiredMinAge());
        return shift;
    }

    List<Shift> toShifts(List<ShiftDto> shiftDtos);

    default Event toEvent(EventRequestDto eventRequestDto) {
        Event event = new Event();
        event.setName(eventRequestDto.getName());
        event.setDescription(eventRequestDto.getDescription());
        event.setPeselVerificationRequired(eventRequestDto.isPeselVerificationRequired());
        event.setAgreementNeeded(eventRequestDto.isAgreementNeeded());
        event.setImageUrl(eventRequestDto.getImageUrl());
        return event;
    }

    default Address toAddress(EventRequestDto eventRequestDto) {
        Address address = new Address();
        address.setStreet(eventRequestDto.getStreet());
        address.setHomeNum(eventRequestDto.getHomeNum());
        address.setAddressDescription(eventRequestDto.getAddressDescription());
        return address;
    }

    default EventResponseDto toEventResponseDto(Event event) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setName(event.getName());
        eventResponseDto.setOrganisation(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        eventResponseDto.setCategories(
                event.getCategories().stream()
                        .map(categoryToEvent -> categoryToEvent.getCategory().getName())
                        .collect(Collectors.toList()));
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setAddressDescription(address.getAddressDescription());
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

        return eventResponseDto;
    }

    default List<ShiftDto> mapShiftListToShiftDtoList(List<Shift> shifts) {
        return shifts.stream().map(this::mapShiftToShiftDto).collect(Collectors.toList());
    }

    default ShiftDto mapShiftToShiftDto(Shift shift) {
        ShiftDto shiftDto = new ShiftDto();
        shiftDto.setStartTime(shift.getStartTime());
        shiftDto.setEndTime(shift.getEndTime());
        shiftDto.setDate(shift.getDate());
        shiftDto.setSignedUp(shift.getRegisteredUsersCount());
        shiftDto.setCapacity(shift.getCapacity());
        shiftDto.setIsLeaderRequired(shift.isLeaderRequired());
        shiftDto.setRequiredMinAge(shift.getRequiredMinAge());
        return shiftDto;
    }

    default EventResponseDetailsDto toEventResponseDetailsDto(Event event) {
        EventResponseDetailsDto eventResponseDto = new EventResponseDetailsDto();
        eventResponseDto.setName(event.getName());
        eventResponseDto.setOrganisationId(event.getOrganisation().getId());
        eventResponseDto.setOrganisationName(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        eventResponseDto.setDescription(event.getDescription());
        eventResponseDto.setCategories(
                event.getCategories().stream()
                        .map(categoryToEvent -> categoryToEvent.getCategory().getId())
                        .collect(Collectors.toList()));
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setHomeNum(address.getHomeNum());
        eventResponseDto.setDistrict(address.getDistrict().getName());
        eventResponseDto.setAddressDescription(address.getAddressDescription());
        eventResponseDto.setImageUrl(event.getImageUrl());
        List<ShiftDto> shifts =
                event.getAddressToEvents().stream()
                        .flatMap(
                                addressToEvent ->
                                        mapShiftListToShiftDtoList(addressToEvent.getShifts())
                                                .stream())
                        .collect(Collectors.toList());

        eventResponseDto.setShifts(shifts);

        return eventResponseDto;
    }
}
