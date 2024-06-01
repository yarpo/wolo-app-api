package pl.pjwstk.woloappapi.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.model.translation.EventTranslationRequest;
import pl.pjwstk.woloappapi.model.translation.EventTranslationResponse;
import pl.pjwstk.woloappapi.model.translation.ReportTranslationResponce;
import pl.pjwstk.woloappapi.model.translation.ShiftTranslation;
import pl.pjwstk.woloappapi.service.CityService;
import pl.pjwstk.woloappapi.service.DistrictService;
import pl.pjwstk.woloappapi.utils.EventMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EventMapperTests {

    private EventMapper eventMapper;

    @BeforeEach
    public void setup() {
        DistrictService districtService = Mockito.mock(DistrictService.class);
        CityService cityService = Mockito.mock(CityService.class);
        eventMapper = new EventMapper(districtService, cityService);
    }

    @Test
    public void testToShiftByShiftRequestDto() {
        var shiftDto = ShiftRequestDto.builder()
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .isLeaderRequired(true)
                .shiftDirections("text")
                .capacity(10)
                .requiredMinAge(18)
                .districtId(1L)
                .street("street")
                .homeNum("1")
                .build();
        var eventDto = EventRequestDto.builder()
                .name("event name")
                .description("event description")
                .organisationId(1L)
                .categories(List.of(1L))
                .isPeselVerificationRequired(true)
                .isAgreementNeeded(true)
                .date(LocalDate.now())
                .imageUrl("url")
                .shifts(List.of(shiftDto))
                .build();

        var translateShift = new ShiftTranslation();
        translateShift.setAddressDescriptionEN("AddressDescriptionEN");
        translateShift.setAddressDescriptionPL("AddressDescriptionPL");
        translateShift.setAddressDescriptionUA("AddressDescriptionUA");
        translateShift.setAddressDescriptionRU("AddressDescriptionRU");

        var translate = new EventTranslationResponse();
        translate.setNamePL("namePL");
        translate.setNameEN("nameEN");
        translate.setNameUA("nameUA");
        translate.setNameRU("nameRU");
        translate.setDescriptionPL("descriptionPL");
        translate.setDescriptionEN("descriptionEN");
        translate.setDescriptionUA("descriptionUA");
        translate.setDescriptionRU("descriptionRU");
        translate.setShiftTranslations(List.of(translateShift));

        var shift= eventMapper.toShift(shiftDto, eventDto, translate).build();

        assertEquals(LocalTime.of(9, 0), shift.getStartTime());
        assertEquals(LocalTime.of(17, 0), shift.getEndTime());
        assertEquals("street", shift.getAddress().getStreet());
        assertEquals("1", shift.getAddress().getHomeNum());
        assertTrue(shift.isLeaderRequired());
        assertEquals(10, shift.getCapacity());
        assertEquals(18, shift.getRequiredMinAge());
        assertEquals("AddressDescriptionPL", shift.getShiftDirectionsPL());
        assertEquals("AddressDescriptionEN", shift.getShiftDirectionsEN());
        assertEquals("AddressDescriptionUA", shift.getShiftDirectionsUA());
        assertEquals("AddressDescriptionRU", shift.getShiftDirectionsRU());
    }

    @Test
    public void testToShiftByShiftEditRequestDto() {
        var shiftDto = new ShiftEditRequestDto();
        shiftDto.setStartTime(LocalTime.of(9, 0));
        shiftDto.setEndTime(LocalTime.of(17, 0));
        shiftDto.setIsLeaderRequired(true);
        shiftDto.setCapacity(10);
        shiftDto.setRequiredMinAge(18);
        shiftDto.setDistrictId(1L);
        shiftDto.setShiftDirectionsPL("directionsPL");
        shiftDto.setShiftDirectionsEN("directionsEN");
        shiftDto.setShiftDirectionsUA("directionsUA");
        shiftDto.setShiftDirectionsRU("directionsRU");
        shiftDto.setStreet("Test Street");
        shiftDto.setHomeNum("123");

        var shift = eventMapper.toShift(shiftDto).build();

        assertEquals(LocalTime.of(9, 0), shift.getStartTime());
        assertEquals(LocalTime.of(17, 0), shift.getEndTime());
        assertTrue(shift.isLeaderRequired());
        assertEquals(10, shift.getCapacity());
        assertEquals(18, shift.getRequiredMinAge());
        assertEquals("directionsPL", shift.getShiftDirectionsPL());
        assertEquals("directionsEN", shift.getShiftDirectionsEN());
        assertEquals("directionsUA", shift.getShiftDirectionsUA());
        assertEquals("directionsRU", shift.getShiftDirectionsRU());
    }

    @Test
    public void testToAddressByShiftRequestDto() {
        var shiftDto = ShiftRequestDto.builder()
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .isLeaderRequired(true)
                .shiftDirections("text")
                .capacity(10)
                .requiredMinAge(18)
                .districtId(1L)
                .street("street")
                .homeNum("1")
                .build();

        var address = eventMapper.toAddress(shiftDto).build();

        assertEquals("street", address.getStreet());
        assertEquals("1", address.getHomeNum());
    }

    @Test
    public void testToAddressByShiftEditRequestDto() {
        var shiftDto = new ShiftEditRequestDto();
        shiftDto.setStartTime(LocalTime.of(9, 0));
        shiftDto.setEndTime(LocalTime.of(17, 0));
        shiftDto.setIsLeaderRequired(true);
        shiftDto.setCapacity(10);
        shiftDto.setRequiredMinAge(18);
        shiftDto.setDistrictId(1L);
        shiftDto.setShiftDirectionsPL("directionsPL");
        shiftDto.setShiftDirectionsEN("directionsEN");
        shiftDto.setShiftDirectionsUA("directionsUA");
        shiftDto.setShiftDirectionsRU("directionsRU");
        shiftDto.setStreet("Test Street");
        shiftDto.setHomeNum("123");

        var address = eventMapper.toAddress(shiftDto).build();

        assertEquals("Test Street", address.getStreet());
        assertEquals("123", address.getHomeNum());
    }

    @Test
    public void testToEventResponseDto() {
        var organisation = Organisation.builder().name("Test Organisation").build();
        var city = City.builder().name("Test City").build();
        var district = District.builder().id(1L).name("district").build();
        var category = Category.builder().name("basic").build();
        var ctu = CategoryToEvent.builder().category(category).build();
        var address = Address.builder()
                .id(1L)
                .street("street")
                .homeNum("123")
                .district(district)
                .build();

        var shift = Shift.builder()
                .id(1L)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .isLeaderRequired(true)
                .shiftDirectionsPL("PL")
                .shiftDirectionsEN("EN")
                .shiftDirectionsUA("UA")
                .shiftDirectionsRU("RU")
                .capacity(10)
                .requiredMinAge(18)
                .registeredUsers(5)
                .address(address)
                .build();
        var event = Event.builder()
                .id(1L)
                .namePL("Test Name PL")
                .nameEN("Test Name EN")
                .nameUA("Test Name UA")
                .nameRU("Test Name RU")
                .descriptionPL("descriptionPL")
                .descriptionEN("descriptionEN")
                .descriptionUA("descriptionUA")
                .descriptionRU("descriptionRU")
                .isPeselVerificationRequired(true)
                .isAgreementNeeded(true)
                .organisation(organisation)
                .city(city)
                .categories(List.of(ctu))
                .date(LocalDate.now())
                .imageUrl("https://example.com/image.jpg")
                .shifts(List.of(shift))
                .build();
        shift.setEvent(event);


        var eventResponseDto = eventMapper.toEventResponseDto(event);

        assertEquals(1L, eventResponseDto.getId());
        assertEquals("Test Name PL", eventResponseDto.getNamePL());
        assertEquals("Test Name EN", eventResponseDto.getNameEN());
        assertEquals("Test Name UA", eventResponseDto.getNameUA());
        assertEquals("Test Name RU", eventResponseDto.getNameRU());
        assertEquals("Test Organisation", eventResponseDto.getOrganisation());
        assertEquals(java.time.LocalDate.now(), eventResponseDto.getDate());
        assertTrue(eventResponseDto.getIsPeselVerificationRequired());
        assertTrue(eventResponseDto.getIsAgreementNeeded());
        assertEquals("Test City", eventResponseDto.getCity());
        assertEquals("https://example.com/image.jpg", eventResponseDto.getImageUrl());
        assertEquals(1, eventResponseDto.getShifts().size());
        assertEquals(1, eventResponseDto.getCategories().size());
        assertEquals("basic", eventResponseDto.getCategories().get(0));
        assertEquals(1L, eventResponseDto.getShifts().get(0).getEventId());
        assertEquals("Test Name PL", eventResponseDto.getShifts().get(0).getEventNamePL());
        assertEquals("Test Name EN", eventResponseDto.getShifts().get(0).getEventNameEN());
        assertEquals("Test Name UA", eventResponseDto.getShifts().get(0).getEventNameUA());
        assertEquals("Test Name RU", eventResponseDto.getShifts().get(0).getEventNameRU());
        assertEquals(LocalDate.now(),eventResponseDto.getShifts().get(0).getDate());
        assertEquals(LocalTime.of(9, 0), eventResponseDto.getShifts().get(0).getStartTime());
        assertEquals(LocalTime.of(17, 0), eventResponseDto.getShifts().get(0).getEndTime());
        assertEquals(10, eventResponseDto.getShifts().get(0).getCapacity());
        assertTrue(eventResponseDto.getShifts().get(0).isLeaderRequired());
        assertEquals(18, eventResponseDto.getShifts().get(0).getRequiredMinAge());
        assertEquals(5, eventResponseDto.getShifts().get(0).getRegisteredUsers());
        assertEquals("district", eventResponseDto.getShifts().get(0).getDistrict());
        assertEquals("street", eventResponseDto.getShifts().get(0).getStreet());
        assertEquals("123", eventResponseDto.getShifts().get(0).getHomeNum());
        assertEquals("Test City", eventResponseDto.getShifts().get(0).getCity());
        assertEquals("PL", eventResponseDto.getShifts().get(0).getShiftDirectionsPL());

    }

    @Test
    public void testToShiftInfoRespons() {
        var category = Category.builder().name("basic").build();
        var ctu = CategoryToEvent.builder().category(category).build();
        var address = Address.builder()
                .id(1L)
                .street("street")
                .homeNum("123")
                .build();

        var shift = Shift.builder()
                .id(1L)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .isLeaderRequired(true)
                .shiftDirectionsPL("PL")
                .shiftDirectionsEN("EN")
                .shiftDirectionsUA("UA")
                .shiftDirectionsRU("RU")
                .capacity(10)
                .requiredMinAge(18)
                .registeredUsers(5)
                .address(address)
                .build();
        var event = Event.builder()
                .id(1L)
                .namePL("Test Name PL")
                .nameEN("Test Name EN")
                .nameUA("Test Name UA")
                .nameRU("Test Name RU")
                .descriptionPL("descriptionPL")
                .descriptionEN("descriptionEN")
                .descriptionUA("descriptionUA")
                .descriptionRU("descriptionRU")
                .isPeselVerificationRequired(true)
                .isAgreementNeeded(true)
                .categories(List.of(ctu))
                .date(LocalDate.now())
                .imageUrl("https://example.com/image.jpg")
                .shifts(List.of(shift))
                .build();
        shift.setEvent(event);


        var shiftInfoRespons = eventMapper.toShiftInfoRespons(shift);

        assertEquals(shift.getId(), shiftInfoRespons.getId());
        assertEquals(shift.getEvent().getDate(), shiftInfoRespons.getDate());
        assertEquals(shift.getStartTime(), shiftInfoRespons.getStartTime());
        assertEquals(shift.getEndTime(), shiftInfoRespons.getEndTime());
        assertEquals(shift.getShiftDirectionsPL(), shiftInfoRespons.getShiftDirectionsPL());
        assertEquals(shift.getShiftDirectionsEN(), shiftInfoRespons.getShiftDirectionsEN());
        assertEquals(shift.getShiftDirectionsUA(), shiftInfoRespons.getShiftDirectionsUA());
        assertEquals(shift.getShiftDirectionsRU(), shiftInfoRespons.getShiftDirectionsRU());
        assertEquals(shift.getEvent().getId(), shiftInfoRespons.getEventId());
        assertEquals(shift.getEvent().getNamePL(), shiftInfoRespons.getEventNamePL());
        assertEquals(shift.getEvent().getNameEN(), shiftInfoRespons.getEventNameEN());
        assertEquals(shift.getEvent().getNameUA(), shiftInfoRespons.getEventNameUA());
        assertEquals(shift.getEvent().getNameRU(), shiftInfoRespons.getEventNameRU());
        assertEquals(shift.getAddress().getStreet() + " " + shift.getAddress().getHomeNum(),
                shiftInfoRespons.getAddress());
    }

    @Test
    public void testToEventResponseDetailsDto() {
        var organisation = Organisation.builder().id(1L).name("Test Organisation").build();
        var city = City.builder().name("Test City").build();
        var district = District.builder().id(1L).name("district").build();
        var category = Category.builder().name("basic").build();
        var ctu = CategoryToEvent.builder().category(category).build();
        var address = Address.builder()
                .id(1L)
                .street("street")
                .homeNum("123")
                .district(district)
                .build();

        var shift = Shift.builder()
                .id(1L)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .isLeaderRequired(true)
                .shiftDirectionsPL("PL")
                .shiftDirectionsEN("EN")
                .shiftDirectionsUA("UA")
                .shiftDirectionsRU("RU")
                .capacity(10)
                .requiredMinAge(18)
                .registeredUsers(5)
                .address(address)
                .build();
        var event = Event.builder()
                .id(1L)
                .namePL("Test Name PL")
                .nameEN("Test Name EN")
                .nameUA("Test Name UA")
                .nameRU("Test Name RU")
                .descriptionPL("descriptionPL")
                .descriptionEN("descriptionEN")
                .descriptionUA("descriptionUA")
                .descriptionRU("descriptionRU")
                .isPeselVerificationRequired(true)
                .isAgreementNeeded(true)
                .organisation(organisation)
                .city(city)
                .categories(List.of(ctu))
                .date(LocalDate.now())
                .imageUrl("https://example.com/image.jpg")
                .shifts(List.of(shift))
                .build();
        shift.setEvent(event);


        var eventResponseDetailsDto = eventMapper.toEventResponseDetailsDto(event);

        assertEquals(1L, eventResponseDetailsDto.getId());
        assertEquals("Test Name PL", eventResponseDetailsDto.getNamePL());
        assertEquals("Test Name EN", eventResponseDetailsDto.getNameEN());
        assertEquals("Test Name UA", eventResponseDetailsDto.getNameUA());
        assertEquals("Test Name RU", eventResponseDetailsDto.getNameRU());
        assertEquals("descriptionPL", eventResponseDetailsDto.getDescriptionPL());
        assertEquals("descriptionEN", eventResponseDetailsDto.getDescriptionEN());
        assertEquals("descriptionUA", eventResponseDetailsDto.getDescriptionUA());
        assertEquals("descriptionRU", eventResponseDetailsDto.getDescriptionRU());
        assertEquals(1L, eventResponseDetailsDto.getOrganisationId());
        assertEquals("Test Organisation", eventResponseDetailsDto.getOrganisationName());
        assertEquals(1L, eventResponseDetailsDto.getOrganisationId());
        assertEquals(java.time.LocalDate.now(), eventResponseDetailsDto.getDate());
        assertTrue(eventResponseDetailsDto.getIsPeselVerificationRequired());
        assertTrue(eventResponseDetailsDto.getIsAgreementNeeded());
        assertEquals("basic", eventResponseDetailsDto.getCategories().get(0));
        assertEquals("https://example.com/image.jpg", eventResponseDetailsDto.getImageUrl());
        assertEquals(1, eventResponseDetailsDto.getShifts().size());
        assertEquals(1, eventResponseDetailsDto.getCategories().size());
        assertEquals("Test City", eventResponseDetailsDto.getCity());
        assertEquals(LocalDate.now(),eventResponseDetailsDto.getShifts().get(0).getDate());
        assertEquals(LocalTime.of(9, 0), eventResponseDetailsDto.getShifts().get(0).getStartTime());
        assertEquals(LocalTime.of(17, 0), eventResponseDetailsDto.getShifts().get(0).getEndTime());
        assertEquals(10, eventResponseDetailsDto.getShifts().get(0).getCapacity());
        assertTrue(eventResponseDetailsDto.getShifts().get(0).isLeaderRequired());
        assertEquals(18, eventResponseDetailsDto.getShifts().get(0).getRequiredMinAge());
        assertEquals(5, eventResponseDetailsDto.getShifts().get(0).getRegisteredUsers());
        assertEquals("district", eventResponseDetailsDto.getShifts().get(0).getDistrict());
        assertEquals("street", eventResponseDetailsDto.getShifts().get(0).getStreet());
        assertEquals("123", eventResponseDetailsDto.getShifts().get(0).getHomeNum());
        assertEquals("Test City", eventResponseDetailsDto.getShifts().get(0).getCity());
        assertEquals("PL", eventResponseDetailsDto.getShifts().get(0).getShiftDirectionsPL());
    }

    @Test
    public void testToShiftResponseDto() {
        Shift shift = new Shift();
        shift.setId(1L);
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(17, 0));
        shift.setCapacity(10);
        shift.setLeaderRequired(true);
        shift.setRequiredMinAge(18);
        shift.setRegisteredUsers(5);

        Event event = new Event();
        event.setId(2L);
        event.setNamePL("Event Name PL");
        event.setNameEN("Event Name EN");
        event.setNameUA("Event Name UA");
        event.setNameRU("Event Name RU");
        event.setDate(LocalDate.now());
        shift.setEvent(event);

        District district = new District();
        district.setName("Test District");

        Address address = new Address();
        address.setStreet("Test Street");
        address.setHomeNum("123");
        address.setDistrict(district);
        shift.setAddress(address);

        shift.setShiftDirectionsPL("Directions PL");
        shift.setShiftDirectionsEN("Directions EN");
        shift.setShiftDirectionsUA("Directions UA");
        shift.setShiftDirectionsRU("Directions RU");

        City city = new City();
        city.setName("Test City");
        event.setCity(city);

        var shiftResponseDto = eventMapper.toShiftResponseDto(shift);

        assertEquals(shift.getId(), shiftResponseDto.getShiftId());
        assertEquals(shift.getEvent().getId(), shiftResponseDto.getEventId());
        assertEquals(shift.getEvent().getNamePL(), shiftResponseDto.getEventNamePL());
        assertEquals(shift.getEvent().getNameEN(), shiftResponseDto.getEventNameEN());
        assertEquals(shift.getEvent().getNameUA(), shiftResponseDto.getEventNameUA());
        assertEquals(shift.getEvent().getNameRU(), shiftResponseDto.getEventNameRU());
        assertEquals(shift.getEvent().getDate(), shiftResponseDto.getDate());
        assertEquals(shift.getStartTime(), shiftResponseDto.getStartTime());
        assertEquals(shift.getEndTime(), shiftResponseDto.getEndTime());
        assertEquals(shift.getCapacity(), shiftResponseDto.getCapacity());
        assertEquals(shift.isLeaderRequired(), shiftResponseDto.isLeaderRequired());
        assertEquals(shift.getRequiredMinAge(), shiftResponseDto.getRequiredMinAge());
        assertEquals(shift.getRegisteredUsers(), shiftResponseDto.getRegisteredUsers());
        assertEquals(shift.getAddress().getDistrict().getName(), shiftResponseDto.getDistrict());
        assertEquals(shift.getAddress().getStreet(), shiftResponseDto.getStreet());
        assertEquals(shift.getAddress().getHomeNum(), shiftResponseDto.getHomeNum());
        assertEquals(shift.getShiftDirectionsPL(), shiftResponseDto.getShiftDirectionsPL());
        assertEquals(shift.getShiftDirectionsEN(), shiftResponseDto.getShiftDirectionsEN());
        assertEquals(shift.getShiftDirectionsUA(), shiftResponseDto.getShiftDirectionsUA());
        assertEquals(shift.getShiftDirectionsRU(), shiftResponseDto.getShiftDirectionsRU());
        assertEquals(shift.getEvent().getCity().getName(), shiftResponseDto.getCity());
    }

    @Test
    public void testToEvent() {
        EventRequestDto dtoEvent = new EventRequestDto();
        dtoEvent.setDate(LocalDate.now());
        dtoEvent.setPeselVerificationRequired(true);
        dtoEvent.setAgreementNeeded(true);
        dtoEvent.setImageUrl("https://example.com/image.jpg");
        dtoEvent.setCityId(1L);

        EventTranslationResponse translation = new EventTranslationResponse();
        translation.setNamePL("Name PL");
        translation.setNameEN("Name EN");
        translation.setNameUA("Name UA");
        translation.setNameRU("Name RU");
        translation.setDescriptionPL("Description PL");
        translation.setDescriptionEN("Description EN");
        translation.setDescriptionUA("Description UA");
        translation.setDescriptionRU("Description RU");

        Event.EventBuilder eventBuilder = eventMapper.toEvent(dtoEvent, translation);

        Event event = eventBuilder.build();
        assertEquals(LocalDate.now(), event.getDate());
        assertTrue(event.isPeselVerificationRequired());
        assertTrue(event.isAgreementNeeded());
        assertEquals("https://example.com/image.jpg", event.getImageUrl());
        assertEquals("Name PL", event.getNamePL());
        assertEquals("Name EN", event.getNameEN());
        assertEquals("Name UA", event.getNameUA());
        assertEquals("Name RU", event.getNameRU());
        assertEquals("Description PL", event.getDescriptionPL());
        assertEquals("Description EN", event.getDescriptionEN());
        assertEquals("Description UA", event.getDescriptionUA());
        assertEquals("Description RU", event.getDescriptionRU());
    }

    @Test
    public void testToReport() {
        ReportRequestDto reportDto = new ReportRequestDto();
        reportDto.setPublished(true);

        ReportTranslationResponce translation = new ReportTranslationResponce();
        translation.setReportPL("Report PL");
        translation.setReportEN("Report EN");
        translation.setReportUA("Report UA");
        translation.setReportRU("Report RU");

        Report.ReportBuilder reportBuilder = eventMapper.toReport(reportDto, translation);

        Report report = reportBuilder.build();
        assertEquals("Report PL", report.getReportPL());
        assertEquals("Report EN", report.getReportEN());
        assertEquals("Report UA", report.getReportUA());
        assertEquals("Report RU", report.getReportRU());
        assertTrue(report.isPublished());
    }

    @Test
    public void testToReportResponceDto() {
        Report report = new Report();
        report.setId(1L);
        report.setPublished(true);
        report.setReportPL("Report PL");
        report.setReportEN("Report EN");
        report.setReportUA("Report UA");
        report.setReportRU("Report RU");

        Event event = new Event();
        event.setId(2L);
        report.setEvent(event);

        ReportResponceDto reportResponceDto = eventMapper.toReportResponceDto(report);

        assertEquals(report.getId(), reportResponceDto.getId());
        assertEquals(report.getEvent().getId(), reportResponceDto.getEvent());
        assertEquals(report.isPublished(), reportResponceDto.isPublished());
        assertEquals(report.getReportPL(), reportResponceDto.getReportPL());
        assertEquals(report.getReportEN(), reportResponceDto.getReportEN());
        assertEquals(report.getReportUA(), reportResponceDto.getReportUA());
        assertEquals(report.getReportRU(), reportResponceDto.getReportRU());
    }

    @Test
    public void testToEventTranslationDto() {
        ShiftRequestDto shiftDto = new ShiftRequestDto();
        shiftDto.setShiftDirections("Shift Directions");

        EventRequestDto dtoEvent = new EventRequestDto();
        dtoEvent.setName("Event Name");
        dtoEvent.setDescription("Event Description");
        dtoEvent.setImageUrl("https://example.com/image.jpg");
        dtoEvent.setShifts(Collections.singletonList(shiftDto));

        String language = "EN";

        EventTranslationRequest translation = eventMapper.toEventTranslationDto(dtoEvent, language);

        assertEquals(dtoEvent.getName(), translation.getName());
        assertEquals(dtoEvent.getDescription(), translation.getDescription());
        assertEquals(shiftDto.getShiftDirections(), translation.getShiftDirections().get(0));
        assertEquals(language, translation.getLanguage());
    }
}
