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

    default Event toEvent(EventTranslationResponsDto translation, EventRequestDto eventRequestDto) {
        Event event = new Event();
        event.setNamePL(translation.getTitlePL());
        event.setNameEN(translation.getTitleEN());
        event.setNameUA(translation.getTitleUA());
        event.setNameRU(translation.getTitleRU());
        event.setDescriptionPL(translation.getDescriptionPL());
        event.setDescriptionEN(translation.getDescriptionEN());
        event.setDescriptionUA(translation.getDescriptionUA());
        event.setDescriptionRU(translation.getDescriptionRU());
        event.setPeselVerificationRequired(eventRequestDto.isPeselVerificationRequired());
        event.setAgreementNeeded(eventRequestDto.isAgreementNeeded());
        event.setImageUrl(eventRequestDto.getImageUrl());
        return event;
    }

//    default Address toAddress(EventTranslationResponsDto translation, EventRequestDto eventRequestDto) {
//        Address address = new Address();
//        address.setStreet(eventRequestDto.getStreet());
//        address.setHomeNum(eventRequestDto.getHomeNum());
//        address.setAddressDescriptionPL(translation.getAddressDescriptionPL());
//        address.setAddressDescriptionEN(translation.getAddressDescriptionEN());
//        address.setAddressDescriptionUA(translation.getAddressDescriptionUA());
//        address.setAddressDescriptionRU(translation.getAddressDescriptionRU());
//        return address;
//    }

    default Address toAddress(EventTranslateRequestDto dtoEvent) {
        Address address = new Address();
        address.setStreet(dtoEvent.getStreet());
        address.setHomeNum(dtoEvent.getHomeNum());
        address.setAddressDescriptionPL(dtoEvent.getAddressDescription_pl());
        address.setAddressDescriptionEN(dtoEvent.getAddressDescription_en());
        address.setAddressDescriptionUA(dtoEvent.getAddressDescription_ua());
        address.setAddressDescriptionRU(dtoEvent.getAddressDescription_ru());
        return address;
    }

    default EventResponseDto toEventResponseDto(Event event, List<String> translations) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setName(translations.get(0));
        eventResponseDto.setOrganisation(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setAddressDescription(translations.get(2));
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
        shiftDto.setStartTime(shift.getStartTime());
        shiftDto.setEndTime(shift.getEndTime());
        shiftDto.setDate(shift.getDate());
        shiftDto.setSignedUp(shift.getRegisteredUsersCount());
        shiftDto.setCapacity(shift.getCapacity());
        shiftDto.setIsLeaderRequired(shift.isLeaderRequired());
        shiftDto.setRequiredMinAge(shift.getRequiredMinAge());
        return shiftDto;
    }

    default EventResponseDetailsDto toEventResponseDetailsDto(Event event, List<String> translations) {
        EventResponseDetailsDto eventResponseDto = new EventResponseDetailsDto();
        eventResponseDto.setName(translations.get(0));
        eventResponseDto.setOrganisationId(event.getOrganisation().getId());
        eventResponseDto.setOrganisationName(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        eventResponseDto.setDescription(translations.get(1));
        eventResponseDto.setCategories(
                event.getCategories().stream()
                        .map(categoryToEvent ->mapCategoryToCategoryDto( categoryToEvent.getCategory()))
                        .collect(Collectors.toList()));
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setHomeNum(address.getHomeNum());
        eventResponseDto.setDistrict(address.getDistrict().getName());
        eventResponseDto.setAddressDescription(translations.get(2));
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

    default EventTranslationRequestDto toEventTranslationDto(EventRequestDto eventDto){
        EventTranslationRequestDto translation = new EventTranslationRequestDto();
        translation.setTitle(eventDto.getName());
        translation.setDescription(eventDto.getDescription());
        translation.setAddressDescription(eventDto.getAddressDescription());
        translation.setLanguage(eventDto.getLanguage());
        return translation;
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

    default Event toEvent(EventTranslateRequestDto dtoEvent){
        Event event = new Event();
        event.setNamePL(dtoEvent.getName_pl());
        event.setNameEN(dtoEvent.getName_en());
        event.setNameUA(dtoEvent.getName_ua());
        event.setNameRU(dtoEvent.getName_ru());
        event.setDescriptionPL(dtoEvent.getDescription_pl());
        event.setDescriptionEN(dtoEvent.getDescription_en());
        event.setDescriptionUA(dtoEvent.getDescription_ua());
        event.setDescriptionRU(dtoEvent.getDescription_ru());
        event.setPeselVerificationRequired(dtoEvent.isPeselVerificationRequired());
        event.setAgreementNeeded(dtoEvent.isAgreementNeeded());
        event.setImageUrl(dtoEvent.getImageUrl());
        return event;
    }
}
