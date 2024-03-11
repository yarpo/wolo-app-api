package pl.pjwstk.woloappapi.utils;

import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
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

    public List<Shift> toShifts(List<ShiftDto> shiftDtos) {
        if ( shiftDtos == null ) {
            return null;
        }

        List<Shift> list = new ArrayList<Shift>( shiftDtos.size() );
        for (ShiftDto shiftDto : shiftDtos) {
            list.add( shiftDtoToShift(shiftDto));
        }
        return list;
    }

    protected Shift shiftDtoToShift(ShiftDto shiftDto) {
        if ( shiftDto == null ) {
            return null;
        }
        Shift.ShiftBuilder shift = Shift.builder();
        shift.id( shiftDto.getId() );
        shift.startTime( shiftDto.getStartTime() );
        shift.endTime( shiftDto.getEndTime() );
        shift.date( shiftDto.getDate() );
        if ( shiftDto.getCapacity() != null ) {
            shift.capacity( shiftDto.getCapacity() );
        }
        if ( shiftDto.getIsLeaderRequired() != null ) {
            shift.isLeaderRequired( shiftDto.getIsLeaderRequired() );
        }
        if ( shiftDto.getRequiredMinAge() != null ) {
            shift.requiredMinAge( shiftDto.getRequiredMinAge() );
        }
        return shift.build();
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
        List<Category> categories =
                event.getCategories().stream()
                        .map(CategoryToEvent::getCategory)
                        .collect(Collectors.toList());
        eventResponseDto.setCategories(mapCategoryListToCategoryDtoList(categories));
        return eventResponseDto;
    }

    public List<ShiftDto> mapShiftListToShiftDtoList(List<Shift> shifts) {
        if ( shifts == null ) {
            return null;
        }
        List<ShiftDto> list = new ArrayList<ShiftDto>( shifts.size() );
        for ( Shift shift : shifts ) {
            list.add( mapShiftToShiftDto( shift ) );
        }
        return list;
    }

    public List<CategoryDto> mapCategoryListToCategoryDtoList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }
        List<CategoryDto> list = new ArrayList<CategoryDto>( categories.size() );
        for ( Category category : categories ) {
            list.add( mapCategoryToCategoryDto( category ) );
        }
        return list;
    }

    public CategoryDto mapCategoryToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());
        return categoryDto;
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

    public EventResponseDetailsDto toEventResponseDetailsDto(Event event) {
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
    public Event.EventBuilder toEvent(EventRequestDto dtoEvent){
        return Event.builder()
                .name(dtoEvent.getName())
                .description(dtoEvent.getDescription())
                .isPeselVerificationRequired(dtoEvent.isPeselVerificationRequired())
                .isAgreementNeeded(dtoEvent.isAgreementNeeded())
                .imageUrl(dtoEvent.getImageUrl());
    }
}
