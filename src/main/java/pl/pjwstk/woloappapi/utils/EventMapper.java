package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.pjwstk.woloappapi.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Shift toShift(ShiftDto shiftDto);
    List<Shift> toShifts(List<ShiftDto> shiftDtos);

    Event toEvent(EventRequestDto eventRequestDto);

    Address toAddress(EventRequestDto eventRequestDto);

    default EventResponseDto toEventResponseDto(Event event) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setName(event.getName());
        eventResponseDto.setOrganisationId(event.getOrganisation().getId());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setAddressDescription(address.getAddressDescription());
        eventResponseDto.setHomeNum(address.getHomeNum());
        eventResponseDto.setDistrictId(address.getDistrict().getId());
        eventResponseDto.setImageUrl(event.getImageUrl());
        List<ShiftDto> shifts = event.getAddressToEvents().stream()
                .flatMap(addressToEvent -> mapShiftListToShiftDtoList(addressToEvent.getShifts()).stream())
                .collect(Collectors.toList());

        eventResponseDto.setShifts(shifts);

        return eventResponseDto;
    }

    default List<ShiftDto> mapShiftListToShiftDtoList(List<Shift> shifts) {
        return shifts.stream()
                .map(this::mapShiftToShiftDto)
                .collect(Collectors.toList());
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
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        eventResponseDto.setDescription(event.getDescription());
        eventResponseDto.setCategories(event.getCategories().stream()
                .map(categoryToEvent -> categoryToEvent.getCategory().getId())
                .collect(Collectors.toList()));
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setHomeNum(address.getHomeNum());
        eventResponseDto.setDistrictId(address.getDistrict().getId());
        eventResponseDto.setImageUrl(event.getImageUrl());
        List<ShiftDto> shifts = event.getAddressToEvents().stream()
                .flatMap(addressToEvent -> mapShiftListToShiftDtoList(addressToEvent.getShifts()).stream())
                .collect(Collectors.toList());

        eventResponseDto.setShifts(shifts);

        return eventResponseDto;
    }
}