package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class EventMapper {
    private static final Map<Language, Function<Event, String>> nameExtractorMap = Map.of(
            Language.PL, Event::getNamePL,
            Language.EN, Event::getNameEN,
            Language.UA, Event::getNameUA,
            Language.RU, Event::getNameRU
    );

    private static final Map<Language, Function<Event, String>> descriptionExtractorMap = Map.of(
            Language.PL, Event::getDescriptionPL,
            Language.EN, Event::getDescriptionEN,
            Language.UA, Event::getDescriptionUA,
            Language.RU, Event::getDescriptionRU
    );

    private static final Map<Language, Function<Address, String>> addressDescriptionExtractorMap = Map.of(
            Language.PL, Address::getAddressDescriptionPL,
            Language.EN, Address::getAddressDescriptionEN,
            Language.UA, Address::getAddressDescriptionUA,
            Language.RU, Address::getAddressDescriptionRU
    );
    public Shift toShift(ShiftDto shiftDto) {
        Shift shift = new Shift();
        shift.setStartTime(shiftDto.getStartTime());
        shift.setEndTime(shiftDto.getEndTime());
        shift.setDate(shiftDto.getDate());
        shift.setLeaderRequired(shiftDto.getIsLeaderRequired());
        shift.setCapacity(shiftDto.getCapacity());
        shift.setRequiredMinAge(shiftDto.getRequiredMinAge());
        return shift;
    }

    public abstract List<Shift> toShifts(List<ShiftDto> shiftDtos);

    public Event toEvent(EventTranslationResponsDto translation, EventRequestDto eventRequestDto) {
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

    public Address toAddress(EventTranslationResponsDto translation, EventRequestDto eventRequestDto) {
        Address address = new Address();
        address.setStreet(eventRequestDto.getStreet());
        address.setHomeNum(eventRequestDto.getHomeNum());
        address.setAddressDescriptionPL(translation.getAddressDescriptionPL());
        address.setAddressDescriptionEN(translation.getAddressDescriptionEN());
        address.setAddressDescriptionUA(translation.getAddressDescriptionUA());
        address.setAddressDescriptionRU(translation.getAddressDescriptionRU());
        return address;
    }

    public EventResponseDto toEventResponseDto(Event event, Language language) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setName(nameExtractorMap.getOrDefault(language, e -> null).apply(event));
        eventResponseDto.setOrganisation(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setAddressDescription(address.getAddressDescriptionPL());
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

    public List<ShiftDto> mapShiftListToShiftDtoList(List<Shift> shifts) {
        return shifts.stream().map(this::mapShiftToShiftDto).collect(Collectors.toList());
    }
    default List<CategoryDto> mapCategoryListToCategoryDtoList(List<Category> categories) {
        return categories.stream().map(this::mapCategoryToCategoryDto).collect(Collectors.toList());
    }
    default CategoryDto mapCategoryToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());
        return categoryDto;
    }


    public ShiftDto mapShiftToShiftDto(Shift shift) {
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

    public EventResponseDetailsDto toEventResponseDetailsDto(Event event, Language language) {
        EventResponseDetailsDto eventResponseDto = new EventResponseDetailsDto();
        eventResponseDto.setName(nameExtractorMap.getOrDefault(language, e -> null).apply(event));
        eventResponseDto.setOrganisationId(event.getOrganisation().getId());
        eventResponseDto.setOrganisationName(event.getOrganisation().getName());
        eventResponseDto.setPeselVerificationRequired(event.isPeselVerificationRequired());
        eventResponseDto.setDescription(descriptionExtractorMap.getOrDefault(language, e -> null).apply(event));
        eventResponseDto.setCategories(
                event.getCategories().stream()
                        .map(categoryToEvent ->mapCategoryToCategoryDto( categoryToEvent.getCategory()))
                        .collect(Collectors.toList()));
        Address address = event.getAddressToEvents().get(0).getAddress();
        eventResponseDto.setStreet(address.getStreet());
        eventResponseDto.setHomeNum(address.getHomeNum());
        eventResponseDto.setDistrict(address.getDistrict().getName());
        eventResponseDto.setName(nameExtractorMap.getOrDefault(language, e -> null).apply(event));
        eventResponseDto.setAddressDescription(addressDescriptionExtractorMap
                .getOrDefault(language, e -> null).apply(address));
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

    public EventTranslationRequestDto toEventTranslationDto(EventRequestDto eventDto){
        EventTranslationRequestDto translation = new EventTranslationRequestDto();
        translation.setTitle(eventDto.getName());
        translation.setDescription(eventDto.getDescription());
        translation.setAddressDescription(eventDto.getAddressDescription());
        translation.setLanguage(eventDto.getLanguage());
        return translation;
    }
}
