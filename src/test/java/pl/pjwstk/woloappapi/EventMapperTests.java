package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.utils.EventMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventMapperTests {

    @Mock
    private EventMapper eventMapper;

    @Test
    public void testToShift() {
        ShiftDto shiftDto = createShiftDto(LocalTime.of(9, 0), LocalTime.of(17, 0),
                LocalDate.now(), 5, true, 10, 18);

        Shift shift = createShift(LocalTime.of(9, 0), LocalTime.of(17, 0),
                LocalDate.now(), true, 10, 18);

        when(eventMapper.toShift(shiftDto)).thenReturn(shift);

        Shift mappedShift = eventMapper.toShift(shiftDto);

        assertShiftsEqual(shift, mappedShift);
    }
    
    @Test
    public void testToEvent() {
        EventRequestDto eventRequestDto = createEventRequestDto("Test Event", "Test Description",
                true, false, "http://example.com/image",
                "Test Street", "Test HomeNum", "Test AddressDescription"
        );
        Event event = createEvent("Test Event", "Test Description",
                true, false, "http://example.com/image"
        );

        when(eventMapper.toEvent(eventRequestDto)).thenReturn(event);

        Event mappedEvent = eventMapper.toEvent(eventRequestDto);

        assertEventsEqual(event, mappedEvent);
    }

    @Test
    public void testToAddress() {
        EventRequestDto eventRequestDto = createEventRequestDto("Test Event", "Test Description",
                true, false, "http://example.com/image",
                "Test Street", "Test HomeNum", "Test AddressDescription"
        );
        Address address = createAddress("Test Street", "Test HomeNum",
                "Test AddressDescription"
        );

        when(eventMapper.toAddress(eventRequestDto)).thenReturn(address);

        Address mappedAddress = eventMapper.toAddress(eventRequestDto);

        assertAddressesEqual(address, mappedAddress);
    }

    @Test
    public void testMapShiftListToShiftDtoList() {
        Shift shift1 = createShift(LocalTime.of(9, 0), LocalTime.of(17, 0),
                LocalDate.now(), true, 10, 18);

        Shift shift2 = createShift(LocalTime.of(10, 0), LocalTime.of(18, 0),
                LocalDate.now(), false, 12, 16);

        List<ShiftDto> ShiftDtoList = Arrays.asList(
                createShiftDto(LocalTime.of(9, 0), LocalTime.of(17, 0),
                        LocalDate.of(2023, 12, 31), 5,
                        true, 10, 18),
                createShiftDto(LocalTime.of(10, 0), LocalTime.of(18, 0),
                        LocalDate.of(2023, 12, 30), 8,
                        false, 12, 16)
        );

        List<Shift> shiftList = Arrays.asList(shift1, shift2);

        when(eventMapper.mapShiftListToShiftDtoList(shiftList)).thenReturn(ShiftDtoList);

        List<ShiftDto> mappedShiftDtoList = eventMapper.mapShiftListToShiftDtoList(shiftList);


        assertShiftDtoEqualsExpected(mappedShiftDtoList.get(0),
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                LocalDate.of(2023, 12, 31),
                5,
                10,
                true,
                18
        );

        assertShiftDtoEqualsExpected(mappedShiftDtoList.get(1),
                LocalTime.of(10, 0),
                LocalTime.of(18, 0),
                LocalDate.of(2023, 12, 30),
                8,
                12,
                false,
                16
        );
    }

    @Test
    public void testMapShiftToShiftDto() {
        Shift shift = createShift(LocalTime.of(8, 0), LocalTime.of(17, 0),
                LocalDate.of(2023, 12, 31), true, 10, 18);
        ShiftDto shiftDto = createShiftDto(LocalTime.of(9, 0), LocalTime.of(17, 0),
                LocalDate.of(2023, 12, 31), 0,
                true, 10, 18);

        when(eventMapper.mapShiftToShiftDto(shift)).thenReturn(shiftDto);

        ShiftDto mappedShiftDto = eventMapper.mapShiftToShiftDto(shift);

        assertShiftDtoEqualsExpected(mappedShiftDto,
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                LocalDate.of(2023, 12, 31),
                0,
                10,
                true,
                18
        );
    }

    @Test
    public void testToEventResponseDto() {
        Event event = createEvent("Test Event", "Test Description",
                true, false, "http://example.com/image"
        );

        EventResponseDto eventResponseDto = createEventResponseDto("Test Event", true,
                "Test Organisation","Test Street", "Test HomeNum", "Test AddressDescription",
                "Test Discrict", "Test City","http://example.com/image"
        );
        Shift shift = createShift(LocalTime.of(9, 0), LocalTime.of(17, 0),
                LocalDate.of(2023, 12, 31), true, 10, 18);

        when(eventMapper.toEventResponseDto(event)).thenReturn(eventResponseDto);

        EventResponseDto mappedEventResponseDto = eventMapper.toEventResponseDto(event);

        assertEventsResponseEqual(eventResponseDto, mappedEventResponseDto);
    }


    @Test
    public void testToEventResponseDetailsDto() {
        Event event = createEvent("Test Event", "Test Description",
                true, false, "http://example.com/image"
        );

        EventResponseDetailsDto eventResponseDetailsDto = createEventDetailsResponseDto("Test Event",
                1L, "Test Organisation",  true, true,
                "Test Street", "Test HomeNum", "Test AddressDescription",
                "Test District",  "http://example.com/image"
        );

        when(eventMapper.toEventResponseDetailsDto(event)).thenReturn(eventResponseDetailsDto);

        EventResponseDetailsDto mappedEventResponseDetailsDto = eventMapper.toEventResponseDetailsDto(event);

        assertEventsDetailsResponseEqual(eventResponseDetailsDto, mappedEventResponseDetailsDto);

    }

    private Event createEvent(String name, String description, boolean peselVerificationRequired,
            boolean agreementNeeded, String imageUrl) {
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setPeselVerificationRequired(peselVerificationRequired);
        event.setAgreementNeeded(agreementNeeded);
        event.setImageUrl(imageUrl);
        return event;
    }

    private EventRequestDto createEventRequestDto(String name, String description, boolean peselVerificationRequired,
            boolean agreementNeeded, String imageUrl, String street, String homeNum, String addressDescription) {
        EventRequestDto eventRequestDto = new EventRequestDto();
        eventRequestDto.setName(name);
        eventRequestDto.setDescription(description);
        eventRequestDto.setPeselVerificationRequired(peselVerificationRequired);
        eventRequestDto.setAgreementNeeded(agreementNeeded);
        eventRequestDto.setImageUrl(imageUrl);
        eventRequestDto.setStreet(street);
        eventRequestDto.setHomeNum(homeNum);
        eventRequestDto.setAddressDescription(addressDescription);
        return eventRequestDto;
    }

    private EventResponseDto createEventResponseDto(String name, boolean peselVerificationRequired, String organisation,
                                                            String street, String homeNum, String addressDescription,
                                                            String district, String city, String imageUrl) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setName(name);
        eventResponseDto.setPeselVerificationRequired(peselVerificationRequired);
        eventResponseDto.setOrganisation(organisation);
        eventResponseDto.setStreet(street);
        eventResponseDto.setHomeNum(homeNum);
        eventResponseDto.setAddressDescription(addressDescription);
        eventResponseDto.setDistrict(district);
        eventResponseDto.setCity(city);
        eventResponseDto.setImageUrl(imageUrl);

        return eventResponseDto;
    }

    private EventResponseDetailsDto createEventDetailsResponseDto(String name, Long organisationId, String organisation,
                                                                  boolean peselVerificationRequired,
                                                                  boolean agreementNeeded, String street,
                                                                  String homeNum, String addressDescription,
                                                                  String district, String imageUrl) {
        EventResponseDetailsDto eventResponseDetailsDto = new EventResponseDetailsDto();
        eventResponseDetailsDto.setName(name);
        eventResponseDetailsDto.setOrganisationId(organisationId);
        eventResponseDetailsDto.setOrganisationName(organisation);
        eventResponseDetailsDto.setPeselVerificationRequired(peselVerificationRequired);
        eventResponseDetailsDto.setAgreementNeeded(agreementNeeded);
        eventResponseDetailsDto.setStreet(street);
        eventResponseDetailsDto.setHomeNum(homeNum);
        eventResponseDetailsDto.setAddressDescription(addressDescription);
        eventResponseDetailsDto.setDistrict(district);
        eventResponseDetailsDto.setImageUrl(imageUrl);

        return eventResponseDetailsDto;
    }

    private void assertEventsEqual(Event expected, Event actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.isPeselVerificationRequired(), actual.isPeselVerificationRequired());
        assertEquals(expected.isAgreementNeeded(), actual.isAgreementNeeded());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
    }

    private void assertEventsResponseEqual(EventResponseDto expected, EventResponseDto actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getOrganisation(), actual.getOrganisation());
        assertEquals(expected.isPeselVerificationRequired(), actual.isPeselVerificationRequired());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getAddressDescription(), actual.getAddressDescription());
        assertEquals(expected.getHomeNum(), actual.getHomeNum());
        assertEquals(expected.getDistrict(), actual.getDistrict());
        assertEquals(expected.getCity(), actual.getCity());
    }

    private void assertEventsDetailsResponseEqual(EventResponseDetailsDto expected, EventResponseDetailsDto actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.isPeselVerificationRequired(), actual.isPeselVerificationRequired());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getAddressDescription(), actual.getAddressDescription());
        assertEquals(expected.getHomeNum(), actual.getHomeNum());
        assertEquals(expected.getDistrict(), actual.getDistrict());
    }

    private Shift createShift(LocalTime startTime, LocalTime endTime, LocalDate date,
            boolean isLeaderRequired, int capacity, int requiredMinAge) {
        Shift shift = new Shift();
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);
        shift.setDate(date);
        shift.setLeaderRequired(isLeaderRequired);
        shift.setCapacity(capacity);
        shift.setRequiredMinAge(requiredMinAge);
        return shift;
    }

    private ShiftDto createShiftDto(LocalTime startTime, LocalTime endTime, LocalDate date, int signedUp,
                                          boolean isLeaderRequired, int capacity, int requiredMinAge) {
        ShiftDto shiftDto = new ShiftDto();
        shiftDto.setStartTime(startTime);
        shiftDto.setEndTime(endTime);
        shiftDto.setDate(date);
        shiftDto.setSignedUp(signedUp);
        shiftDto.setIsLeaderRequired(isLeaderRequired);
        shiftDto.setCapacity(capacity);
        shiftDto.setRequiredMinAge(requiredMinAge);
        return shiftDto;
    }

    private void assertShiftsEqual(Shift expected, Shift actual) {
        assertEquals(expected.getStartTime(), actual.getStartTime());
        assertEquals(expected.getEndTime(), actual.getEndTime());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.isLeaderRequired(), actual.isLeaderRequired());
        assertEquals(expected.getCapacity(), actual.getCapacity());
        assertEquals(expected.getRequiredMinAge(), actual.getRequiredMinAge());
    }

    private void assertShiftDtoEqualsExpected(ShiftDto shiftDto, LocalTime startTime, LocalTime endTime,
                                              LocalDate date, int signedUp, int capacity,
                                              boolean isLeaderRequired, int requiredMinAge) {
        assertEquals(startTime, shiftDto.getStartTime());
        assertEquals(endTime, shiftDto.getEndTime());
        assertEquals(date, shiftDto.getDate());
        assertEquals(signedUp, shiftDto.getSignedUp());
        assertEquals(capacity, shiftDto.getCapacity());
        assertEquals(isLeaderRequired, shiftDto.getIsLeaderRequired());
        assertEquals(requiredMinAge, shiftDto.getRequiredMinAge());
    }

    private Address createAddress(String street, String homeNum, String addressDescription) {
        Address address = new Address();
        address.setStreet(street);
        address.setHomeNum(homeNum);
        address.setAddressDescription(addressDescription);
        return address;
    }

    private District createDistrict(String name, String city) {
        District district = new District();
        district.setName(name);
        district.setCity(city);
        return district;
    }

    private void assertAddressesEqual(Address expected, Address actual) {
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getHomeNum(), actual.getHomeNum());
        assertEquals(expected.getAddressDescription(), actual.getAddressDescription());
    }
}
