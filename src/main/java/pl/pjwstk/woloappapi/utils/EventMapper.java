package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.*;

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
                .requiredMinAge(shiftDto.getRequiredMinAge());
    }

    List<Shift> toShifts(List<ShiftDto> shiftDtos);

    default Address.AddressBuilder toAddress(EventRequestDto dtoEvent) {
        return Address.builder()
                .street(dtoEvent.getStreet())
                .homeNum(dtoEvent.getHomeNum())
                .addressDescription(dtoEvent.getAddressDescription());
    }

    default EventResponseDto toEventResponseDto(Event event) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setName(event.getName());
        eventResponseDto.setOrganisation(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
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
                        .map(categoryToEvent ->mapCategoryToCategoryDto( categoryToEvent.getCategory()))
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


    default EventAIRequest toEventAIRequest(Event event){
        EventAIRequest aiRequest = new EventAIRequest();
        aiRequest.setId(event.getId());
        aiRequest.setDistrict(event.getAddressToEvents().get(0).getAddress().getDistrict().getName());
        aiRequest.setOrganisation(event.getOrganisation().getName());
        aiRequest.setCategories(event.getCategories()
                .stream()
                .map(cte -> cte
                        .getCategory()
                        .getName())
                .distinct()
                .collect(Collectors.toList()));
        return aiRequest;
    }

    default Event.EventBuilder toEvent(EventRequestDto dtoEvent){
        return Event.builder()
                .name(dtoEvent.getName())
                .description(dtoEvent.getDescription())
                .isPeselVerificationRequired(dtoEvent.isPeselVerificationRequired())
                .isAgreementNeeded(dtoEvent.isAgreementNeeded())
                .imageUrl(dtoEvent.getImageUrl());
    }

    default EventAIRequest toEventAIRequest(Event event){
        EventAIRequest aiRequest = new EventAIRequest();
        aiRequest.setId(event.getId());
        aiRequest.setDistrict(event.getAddressToEvents().get(0).getAddress().getDistrict().getName());
        aiRequest.setOrganisation(event.getOrganisation().getName());
        aiRequest.setCategories(event.getCategories()
                .stream()
                .map(cte -> cte
                        .getCategory()
                        .getName())
                .distinct()
                .collect(Collectors.toList()));
        return aiRequest;
    }
}
