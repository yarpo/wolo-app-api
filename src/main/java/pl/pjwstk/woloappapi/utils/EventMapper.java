package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventMapper {
    default Shift.ShiftBuilder toShift (ShiftDto shiftDto){
        return Shift.builder()
                .startTime(shiftDto.getStartTime())
                .endTime(shiftDto.getEndTime())
                .date(shiftDto.getDate())
                .isLeaderRequired(shiftDto.getIsLeaderRequired())
                .capacity(shiftDto.getCapacity())
                .requiredMinAge(shiftDto.getRequiredMinAge())
                .shiftDirections(shiftDto.getShiftDirections());
    }

    List<Shift> toShifts(List<ShiftDto> shiftDtos);

    default Address.AddressBuilder toAddress(EventRequestDto dtoEvent) {
        return Address.builder()
                .street(dtoEvent.getStreet())
                .homeNum(dtoEvent.getHomeNum());
    }

    default EventResponseDto toEventResponseDto(Event event) {
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
        List<Category> categories =
                event.getCategories().stream()
                        .map(CategoryToEvent::getCategory)
                        .collect(Collectors.toList());
        eventResponseDto.setCategories(mapCategoryListToCategoryDtoList(categories));



        return eventResponseDto;
    }

    List<ShiftDto> mapShiftListToShiftDtoList(List<Shift> shifts) ;
    List<CategoryDto> mapCategoryListToCategoryDtoList(List<Category> categories);
    default CategoryDto mapCategoryToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());
        return categoryDto;
    }


    default ShiftDto mapShiftToShiftDto(Shift shift) {
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

    default List<ShiftResponseDto> toShiftResponseDto(Event event){
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
                shiftResponseDto.setEventName(event.getName());
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

    default EventResponseDetailsDto toEventResponseDetailsDto(Event event) {
        EventResponseDetailsDto eventResponseDto = new EventResponseDetailsDto();
        eventResponseDto.setName(event.getName());
        eventResponseDto.setOrganisationId(event.getOrganisation().getId());
        eventResponseDto.setOrganisationName(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        eventResponseDto.setDescription(event.getDescription());
        eventResponseDto.setCategories(
                event.getCategories().stream()
                        .map(categoryToEvent ->mapCategoryToCategoryDto( categoryToEvent.getCategory()))
                        .collect(Collectors.toList()));
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setHomeNum(address.getHomeNum());
        eventResponseDto.setDistrict(address.getDistrict().getName());
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
    default Event.EventBuilder toEvent(EventRequestDto dtoEvent){
        return Event.builder()
                .name(dtoEvent.getName())
                .description(dtoEvent.getDescription())
                .isPeselVerificationRequired(dtoEvent.isPeselVerificationRequired())
                .isAgreementNeeded(dtoEvent.isAgreementNeeded())
                .imageUrl(dtoEvent.getImageUrl());
    }
}
