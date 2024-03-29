package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.Shift;
import pl.pjwstk.woloappapi.model.ShiftDto;
import pl.pjwstk.woloappapi.model.ShiftToUser;
import pl.pjwstk.woloappapi.utils.EventMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class EventMapperTests {
    private EventMapper eventMapper;
    private EventMapper createEventMapper() {

        return new EventMapper();
    }

    @Test
    public void testToShift() {
        EventMapper eventMapper = createEventMapper();
        ShiftDto shiftDto = new ShiftDto();
        shiftDto.setStartTime(LocalTime.of(9, 0));
        shiftDto.setEndTime(LocalTime.of(17, 0));
        shiftDto.setDate(LocalDate.now());
        shiftDto.setIsLeaderRequired(true);
        shiftDto.setCapacity(10);
        shiftDto.setRequiredMinAge(18);

        Shift shift = eventMapper.toShift(shiftDto).build();

        assertNotNull(shift);
        assertEquals(LocalTime.of(9, 0), shift.getStartTime());
        assertEquals(LocalTime.of(17, 0), shift.getEndTime());
        assertEquals(LocalDate.now(), shift.getDate());
        assertTrue(shift.isLeaderRequired());
        assertEquals(10, shift.getCapacity());
        assertEquals(18, shift.getRequiredMinAge());
    }



//    @Test
//    public void testToEvent() {
//        EventRequestDto eventRequestDto = new EventRequestDto();
//        eventRequestDto.setPeselVerificationRequired(true);
//        eventRequestDto.setAgreementNeeded(false);
//        eventRequestDto.setImageUrl("http://example.com/image");
//
//        Event event = eventMapper.toEvent(translation, eventRequestDto);
//
//        assertNotNull(event);
//        assertEquals("Title PL", event.getNamePL());
//        assertEquals("Title EN", event.getNameEN());
//        assertEquals("Title UA", event.getNameUA());
//        assertEquals("Title RU", event.getNameRU());
//        assertEquals("Description PL", event.getDescriptionPL());
//        assertEquals("Description EN", event.getDescriptionEN());
//        assertEquals("Description UA", event.getDescriptionUA());
//        assertEquals("Description RU", event.getDescriptionRU());
//        assertTrue(event.isPeselVerificationRequired());
//        assertFalse(event.isAgreementNeeded());
//        assertEquals("http://example.com/image", event.getImageUrl());
//        assertFalse(event.isApproved());
//    }

//    @Test
//    public void testToAddress() {
//        EventRequestDto eventRequestDto = new EventRequestDto();
//        eventRequestDto.setStreet("Test Street");
//        eventRequestDto.setHomeNum("123");
//
//        Address address = eventMapper.toAddress(translation, eventRequestDto);
//
//        assertNotNull(address);
//        assertEquals("Test Street", address.getStreet());
//        assertEquals("123", address.getHomeNum());
//        assertEquals("PL Address Description", address.getAddressDescriptionPL());
//        assertEquals("EN Address Description", address.getAddressDescriptionEN());
//        assertEquals("UA Address Description", address.getAddressDescriptionUA());
//        assertEquals("RU Address Description", address.getAddressDescriptionRU());
//    }

    /*
    @Test
    public void testToEventResponseDtoStructure() {
        Event event = new Event();
        event.setId(1L);
        event.setNamePL("PL Name");
        event.setNameEN("EN Name");
        event.setNameUA("UA Name");
        event.setNameRU("RU Name");
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
        address.setAddressDescriptionPL("PL Address Description");
        address.setAddressDescriptionEN("EN Address Description");
        address.setAddressDescriptionUA("UA Address Description");
        address.setAddressDescriptionRU("RU Address Description");
        address.setDistrict(district);

        List<AddressToEvent> addressToEvents = new ArrayList<>();
        AddressToEvent addressToEvent = new AddressToEvent();
        addressToEvent.setAddress(address);
        addressToEvents.add(addressToEvent);
        event.setAddressToEvents(addressToEvents);

        event.setImageUrl("http://example.com/image.jpg");

        List<String> translations = new ArrayList<>();
        translations.add("PL Name");
        translations.add("Description PL");
        translations.add("PL Address Description");

        EventResponseDto eventResponseDto = eventMapper.toEventResponseDto(event, translations);

        assertNotNull(eventResponseDto);
        assertNotNull(eventResponseDto.getId());
        assertNotNull(eventResponseDto.getName());
        assertNotNull(eventResponseDto.getOrganisation());
        assertNotNull(eventResponseDto.getStreet());
        assertNotNull(eventResponseDto.getHomeNum());
        assertNotNull(eventResponseDto.getAddressDescription());
        assertNotNull(eventResponseDto.getDistrict());
        assertNotNull(eventResponseDto.getCity());
        assertNotNull(eventResponseDto.getImageUrl());
        assertNotNull(eventResponseDto.getShifts());
    }

     */

    @Test
    public void testMapShiftToShiftDto() {
        EventMapper eventMapper = createEventMapper();
        Shift shift = new Shift();
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(17, 0));
        shift.setDate(LocalDate.now());
        shift.setCapacity(10);
        shift.setLeaderRequired(true);
        shift.setRequiredMinAge(18);

        List<ShiftToUser> shiftToUsers = new ArrayList<>();
        ShiftToUser shiftToUser = new ShiftToUser();
        shiftToUser.setShift(shift);
        shiftToUsers.add(shiftToUser);
        shift.setShiftToUsers(shiftToUsers);

        ShiftDto shiftDto = eventMapper.mapShiftToShiftDto(shift);

        assertNotNull(shiftDto);
        assertEquals(LocalTime.of(9, 0), shiftDto.getStartTime());
        assertEquals(LocalTime.of(17, 0), shiftDto.getEndTime());
        assertEquals(LocalDate.now(), shiftDto.getDate());
        assertEquals(1, shiftDto.getSignedUp());
        assertEquals(10, shiftDto.getCapacity());
        assertTrue(shiftDto.getIsLeaderRequired());
        assertEquals(18, shiftDto.getRequiredMinAge());
    }


    @Test
    public void testMapShiftListToShiftDtoList() {
        EventMapper eventMapper = createEventMapper();
        List<Shift> shiftList = new ArrayList<>();
        Shift shift1 = new Shift();
        shift1.setStartTime(LocalTime.of(9, 0));
        shift1.setEndTime(LocalTime.of(17, 0));
        shift1.setDate(LocalDate.now());
        shift1.setCapacity(10);
        shift1.setLeaderRequired(true);
        shift1.setRequiredMinAge(18);

        List<ShiftToUser> shiftToUsers1 = new ArrayList<>();
        ShiftToUser shiftToUser1 = new ShiftToUser();
        shiftToUser1.setShift(shift1);
        shiftToUsers1.add(shiftToUser1);
        shift1.setShiftToUsers(shiftToUsers1);

        shiftList.add(shift1);

        Shift shift2 = new Shift();
        shift2.setStartTime(LocalTime.of(10, 0));
        shift2.setEndTime(LocalTime.of(18, 0));
        shift2.setDate(LocalDate.now());
        shift2.setCapacity(12);
        shift2.setLeaderRequired(false);
        shift2.setRequiredMinAge(20);

        List<ShiftToUser> shiftToUsers2 = new ArrayList<>();
        ShiftToUser shiftToUser2 = new ShiftToUser();
        shiftToUser2.setShift(shift2);
        shiftToUsers2.add(shiftToUser2);
        shift2.setShiftToUsers(shiftToUsers2);

        shiftList.add(shift2);

//        List<ShiftDto> shiftDtoList = eventMapper.mapShiftListToShiftDtoList(shiftList);
//
//        assertNotNull(shiftDtoList);
//        assertEquals(2, shiftDtoList.size());
//
//        ShiftDto shiftDto1 = shiftDtoList.get(0);
//        assertNotNull(shiftDto1);
//        assertEquals(LocalTime.of(9, 0), shiftDto1.getStartTime());
//        assertEquals(LocalTime.of(17, 0), shiftDto1.getEndTime());
//        assertEquals(LocalDate.now(), shiftDto1.getDate());
//        assertEquals(1, shiftDto1.getSignedUp());
//        assertEquals(10, shiftDto1.getCapacity());
//        assertTrue(shiftDto1.getIsLeaderRequired());
//        assertEquals(18, shiftDto1.getRequiredMinAge());
//
//        ShiftDto shiftDto2 = shiftDtoList.get(1);
//        assertNotNull(shiftDto2);
//        assertEquals(LocalTime.of(10, 0), shiftDto2.getStartTime());
//        assertEquals(LocalTime.of(18, 0), shiftDto2.getEndTime());
//        assertEquals(LocalDate.now(), shiftDto2.getDate());
//        assertEquals(1, shiftDto2.getSignedUp());
//        assertEquals(12, shiftDto2.getCapacity());
//        assertFalse(shiftDto2.getIsLeaderRequired());
//        assertEquals(20, shiftDto2.getRequiredMinAge());
    }

    /*
    @Test
    public void testToEventResponseDetailsDto() {
        Event event = new Event();
        event.setNamePL("PL Name");
        event.setNameEN("EN Name");
        event.setNameUA("UA Name");
        event.setNameRU("RU Name");
        event.setDescriptionPL("Description PL");
        event.setDescriptionEN("Description EN");
        event.setDescriptionUA("Description UA");
        event.setDescriptionRU("Description RU");
        event.setPeselVerificationRequired(true);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Org Name");
        event.setOrganisation(organisation);

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

        District district = new District();
        district.setName("Test District");

        Address address = new Address();
        address.setStreet("Test Street");
        address.setHomeNum("123");
        address.setAddressDescriptionPL("PL Address Description");
        address.setAddressDescriptionEN("EN Address Description");
        address.setAddressDescriptionUA("UA Address Description");
        address.setAddressDescriptionRU("RU Address Description");
        address.setDistrict(district);

        List<AddressToEvent> addressToEvents = new ArrayList<>();
        AddressToEvent addressToEvent = new AddressToEvent();
        addressToEvent.setAddress(address);

        List<Shift> shifts = new ArrayList<>();
        Shift shift1 = new Shift();
        shift1.setStartTime(LocalTime.of(9, 0));
        shift1.setEndTime(LocalTime.of(17, 0));
        shifts.add(shift1);

        addressToEvent.setShifts(shifts);
        addressToEvents.add(addressToEvent);
        event.setAddressToEvents(addressToEvents);

        event.setImageUrl("http://example.com/image.jpg");

        List<String> translations = new ArrayList<>();
        translations.add("UA Name");
        translations.add("Description UA");
        translations.add("UA Address Description");

        EventResponseDetailsDto eventResponseDetailsDto = eventMapper.toEventResponseDetailsDto(event, translations);

        assertNotNull(eventResponseDetailsDto);
        assertEquals("UA Name", eventResponseDetailsDto.getName());
        assertEquals(1L, eventResponseDetailsDto.getOrganisationId());
        assertEquals("Org Name", eventResponseDetailsDto.getOrganisationName());
        assertTrue(eventResponseDetailsDto.isPeselVerificationRequired());
        assertEquals("Description UA", eventResponseDetailsDto.getDescription());
        assertEquals(2, eventResponseDetailsDto.getCategories().size());
        assertEquals("Test Street", eventResponseDetailsDto.getStreet());
        assertEquals("123", eventResponseDetailsDto.getHomeNum());
        assertEquals("Test District", eventResponseDetailsDto.getDistrict());
        assertEquals("UA Address Description", eventResponseDetailsDto.getAddressDescription());
        assertEquals("http://example.com/image.jpg", eventResponseDetailsDto.getImageUrl());
        assertEquals(1, eventResponseDetailsDto.getShifts().size());
    }

     */
}
