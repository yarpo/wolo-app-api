package pl.pjwstk.woloappapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.utils.EventMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class EventMapperTests {

    EventMapper eventMapper = new EventMapper();

    @Test
    public void testToShift() {
        ShiftRequestDto shiftDto = new ShiftRequestDto();
        shiftDto.setStartTime(LocalTime.of(9, 0));
        shiftDto.setEndTime(LocalTime.of(17, 0));
        shiftDto.setDate(LocalDate.now());
        shiftDto.setIsLeaderRequired(true);
        shiftDto.setCapacity(10);
        shiftDto.setRequiredMinAge(18);

        Shift shift = eventMapper.toShift(shiftDto).build();

        assertEquals(LocalTime.of(9, 0), shift.getStartTime());
        assertEquals(LocalTime.of(17, 0), shift.getEndTime());
        assertEquals(LocalDate.now(), shift.getDate());
        assertTrue(shift.isLeaderRequired());
        assertEquals(10, shift.getCapacity());
        assertEquals(18, shift.getRequiredMinAge());
    }

    @Test
    public void testToAddress() {
        EventRequestDto eventRequestDto = new EventRequestDto();
        eventRequestDto.setStreet("Test Street");
        eventRequestDto.setHomeNum("123");

        Address address = eventMapper.toAddress(eventRequestDto).build();

        assertEquals("Test Street", address.getStreet());
        assertEquals("123", address.getHomeNum());
    }

    @Test
    public void testToEventResponseDto() {
        Event event = new Event();
        event.setId(1L);
        event.setName("Test Name");
        event.setPeselVerificationRequired(true);

        Organisation organisation = new Organisation();
        organisation.setName("Org Name");
        event.setOrganisation(organisation);

        District district = new District();
        district.setName("Test Name");
        district.setCity("Test City");

        Address address = new Address();
        address.setStreet("Test Street");
        address.setHomeNum("123");
        address.setDistrict(district);

        List<AddressToEvent> addressToEvents = new ArrayList<>();
        AddressToEvent addressToEvent = new AddressToEvent();
        addressToEvent.setAddress(address);
        addressToEvents.add(addressToEvent);
        event.setAddressToEvents(addressToEvents);

        List<CategoryToEvent> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        CategoryToEvent categoryToEvent1 = new CategoryToEvent();
        categoryToEvent1.setCategory(category1);

        CategoryToEvent categoryToEvent2 = new CategoryToEvent();
        categoryToEvent2.setCategory(category2);

        categories.add(categoryToEvent1);
        categories.add(categoryToEvent2);
        event.setCategories(categories);

        List<Shift> shifts = new ArrayList<>();

        Shift shift1 = new Shift();
        shift1.setStartTime(LocalTime.of(18, 0));
        shift1.setEndTime(LocalTime.of(23, 0));
        shifts.add(shift1);

        Shift shift2 = new Shift();
        shift2.setStartTime(LocalTime.of(9, 0));
        shift2.setEndTime(LocalTime.of(17, 0));
        shifts.add(shift2);

        addressToEvent.setShifts(shifts);
        addressToEvents.add(addressToEvent);
        event.setAddressToEvents(addressToEvents);

        event.setImageUrl("http://example.com/image.jpg");

        EventResponseDto eventResponseDto = eventMapper.toEventResponseDto(event);

        assertEquals(Long.valueOf(1L), eventResponseDto.getId());
        assertEquals("Test Name", eventResponseDto.getName());
        assertEquals("Org Name", eventResponseDto.getOrganisation());
        assertTrue(eventResponseDto.isPeselVerificationRequired());
        assertEquals("Test Street", eventResponseDto.getStreet());
        assertEquals("123", eventResponseDto.getHomeNum());
        assertEquals("Test Name", eventResponseDto.getDistrict());
        assertEquals("Test City", eventResponseDto.getCity());
        assertEquals("http://example.com/image.jpg", eventResponseDto.getImageUrl());
        assertEquals(4, eventResponseDto.getShifts().size());
        assertEquals(2, eventResponseDto.getCategories().size());
    }

    @Test
    public void testMapShiftListToShiftDtoList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Shift> shiftList = new ArrayList<>();
        Method method = EventMapper.class.getDeclaredMethod("mapShiftListToShiftDtoList", List.class);
        method.setAccessible(true);

        Shift shift1 = new Shift();
        shift1.setStartTime(LocalTime.of(9, 0));
        shift1.setEndTime(LocalTime.of(17, 0));
        shift1.setDate(LocalDate.now());
        shift1.setRegisteredUsers(1);
        shift1.setCapacity(10);
        shift1.setLeaderRequired(true);
        shift1.setRequiredMinAge(18);

        shiftList.add(shift1);

        Shift shift2 = new Shift();
        shift2.setStartTime(LocalTime.of(10, 0));
        shift2.setEndTime(LocalTime.of(18, 0));
        shift2.setDate(LocalDate.now());
        shift2.setRegisteredUsers(1);
        shift2.setCapacity(12);
        shift2.setLeaderRequired(false);
        shift2.setRequiredMinAge(20);

        shiftList.add(shift2);

        //List<ShiftDto> shiftDtoList = eventMapper.mapShiftListToShiftDtoList(shiftList);

        @SuppressWarnings("unchecked")
        List<ShiftRequestDto> shiftDtoList = (List<ShiftRequestDto>) method.invoke(eventMapper, shiftList);

        assertEquals(2, shiftDtoList.size());

        ShiftRequestDto shiftDto1 = shiftDtoList.get(0);
        assertEquals(LocalTime.of(9, 0), shiftDto1.getStartTime());
        assertEquals(LocalTime.of(17, 0), shiftDto1.getEndTime());
        assertEquals(LocalDate.now(), shiftDto1.getDate());
        assertEquals(1, shiftDto1.getSignedUp());
        assertEquals(10, shiftDto1.getCapacity());
        assertTrue(shiftDto1.getIsLeaderRequired());
        assertEquals(18, shiftDto1.getRequiredMinAge());

        ShiftRequestDto shiftDto2 = shiftDtoList.get(1);
        assertEquals(LocalTime.of(10, 0), shiftDto2.getStartTime());
        assertEquals(LocalTime.of(18, 0), shiftDto2.getEndTime());
        assertEquals(LocalDate.now(), shiftDto2.getDate());
        assertEquals(1, shiftDto2.getSignedUp());
        assertEquals(12, shiftDto2.getCapacity());
        assertFalse(shiftDto2.getIsLeaderRequired());
        assertEquals(20, shiftDto2.getRequiredMinAge());
    }

    @Test
    public void testMapShiftToShiftDto() {
        Shift shift = new Shift();
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(17, 0));
        shift.setDate(LocalDate.now());
        shift.setRegisteredUsers(1);
        shift.setCapacity(10);
        shift.setLeaderRequired(true);
        shift.setRequiredMinAge(18);

        ShiftRequestDto shiftDto = eventMapper.mapShiftToShiftDto(shift);

        assertEquals(LocalTime.of(9, 0), shiftDto.getStartTime());
        assertEquals(LocalTime.of(17, 0), shiftDto.getEndTime());
        assertEquals(LocalDate.now(), shiftDto.getDate());
        assertEquals(1, shiftDto.getSignedUp());
        assertEquals(10, shiftDto.getCapacity());
        assertTrue(shiftDto.getIsLeaderRequired());
        assertEquals(18, shiftDto.getRequiredMinAge());
    }

    @Test
    public void testToEventDetailsResponseDto() {
        Event event = new Event();
        event.setName("Test Name");
        event.setDescription("Test Description");
        event.setPeselVerificationRequired(true);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Org Name");
        event.setOrganisation(organisation);

        District district = new District();
        district.setName("Test Name");
        district.setCity("Test City");

        Address address = new Address();
        address.setStreet("Test Street");
        address.setHomeNum("123");
        address.setDistrict(district);

        List<AddressToEvent> addressToEvents = new ArrayList<>();
        AddressToEvent addressToEvent = new AddressToEvent();
        addressToEvent.setAddress(address);
        addressToEvents.add(addressToEvent);
        event.setAddressToEvents(addressToEvents);

        List<CategoryToEvent> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        CategoryToEvent categoryToEvent1 = new CategoryToEvent();
        categoryToEvent1.setCategory(category1);

        CategoryToEvent categoryToEvent2 = new CategoryToEvent();
        categoryToEvent2.setCategory(category2);

        categories.add(categoryToEvent1);
        categories.add(categoryToEvent2);
        event.setCategories(categories);

        List<Shift> shifts = new ArrayList<>();

        Shift shift1 = new Shift();
        shift1.setStartTime(LocalTime.of(18, 0));
        shift1.setEndTime(LocalTime.of(23, 0));
        shifts.add(shift1);

        Shift shift2 = new Shift();
        shift2.setStartTime(LocalTime.of(9, 0));
        shift2.setEndTime(LocalTime.of(17, 0));
        shifts.add(shift2);

        addressToEvent.setShifts(shifts);
        addressToEvents.add(addressToEvent);
        event.setAddressToEvents(addressToEvents);

        event.setImageUrl("http://example.com/image.jpg");

        EventResponseDetailsDto eventResponseDetailsDto = eventMapper.toEventResponseDetailsDto(event);

        assertEquals("Test Name", eventResponseDetailsDto.getName());
        assertEquals(1L, eventResponseDetailsDto.getOrganisationId());
        assertEquals("Org Name", eventResponseDetailsDto.getOrganisationName());
        assertTrue(eventResponseDetailsDto.isPeselVerificationRequired());
        assertEquals("Test Street", eventResponseDetailsDto.getStreet());
        assertEquals("123", eventResponseDetailsDto.getHomeNum());
        assertEquals("Test Name", eventResponseDetailsDto.getDistrict());
        assertEquals("http://example.com/image.jpg", eventResponseDetailsDto.getImageUrl());
        assertEquals(4, eventResponseDetailsDto.getShifts().size());
        assertEquals(2, eventResponseDetailsDto.getCategories().size());
    }

    @Test
    public void testToEvent(){
        EventRequestDto dtoEvent = new EventRequestDto();
        dtoEvent.setName("Test Name");
        dtoEvent.setDescription("Test Description");
        dtoEvent.setPeselVerificationRequired(true);
        dtoEvent.setAgreementNeeded(true);
        dtoEvent.setImageUrl("http://example.com/image.jpg");

        Event event = eventMapper.toEvent(dtoEvent).build();
        assertEquals("Test Name", event.getName());
        assertEquals("Test Description", event.getDescription());
        assertTrue(event.isPeselVerificationRequired());
        assertTrue(event.isAgreementNeeded());
        assertEquals("http://example.com/image.jpg", event.getImageUrl());
    }

    @Test
    public void testToShifts() {
        List<ShiftRequestDto> shiftDtoList = new ArrayList<>();

        ShiftRequestDto shiftDto1 = new ShiftRequestDto();
        shiftDto1.setStartTime(LocalTime.of(9, 0));
        shiftDto1.setEndTime(LocalTime.of(17, 0));
        shiftDto1.setDate(LocalDate.now());
        shiftDto1.setIsLeaderRequired(true);
        shiftDto1.setCapacity(10);
        shiftDto1.setRequiredMinAge(18);

        ShiftRequestDto shiftDto2 = new ShiftRequestDto();
        shiftDto2.setStartTime(LocalTime.of(10, 0));
        shiftDto2.setEndTime(LocalTime.of(18, 0));
        shiftDto2.setDate(LocalDate.now());
        shiftDto2.setIsLeaderRequired(false);
        shiftDto2.setCapacity(12);
        shiftDto2.setRequiredMinAge(18);

        shiftDtoList.add(shiftDto1);
        shiftDtoList.add(shiftDto2);

        List<Shift> shifts = eventMapper.toShifts(shiftDtoList);

        assertEquals(2, shifts.size());
    }

}
