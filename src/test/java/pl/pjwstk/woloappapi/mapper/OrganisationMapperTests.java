package pl.pjwstk.woloappapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.OrganisationRequestDto;
import pl.pjwstk.woloappapi.model.OrganisationResponseDto;
import pl.pjwstk.woloappapi.model.admin.OrganisationResponseAdminDto;
import pl.pjwstk.woloappapi.model.entities.City;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.model.translation.OrganisationTranslationResponce;
import pl.pjwstk.woloappapi.model.entities.Address;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class OrganisationMapperTests {

    private final OrganisationMapper organisationMapper = new OrganisationMapper();

    @Test
    public void testToOrganisationResponseDto() {
        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescriptionPL("Test Description pl");
        organisation.setDescriptionEN("Test Description en");
        organisation.setDescriptionUA("Test Description ua");
        organisation.setDescriptionRU("Test Description ru");
        organisation.setEmail("test@example.com");
        organisation.setPhoneNumber("123456789");
        var city = City.builder().id(1L).build();
        var district = District.builder()
                .id(1L)
                .city(city)
                .build();
        var address = Address.builder()
                .street("Test Street")
                .homeNum("123")
                .district(district)
                .build();
        organisation.setAddress(address);
        organisation.setLogoUrl("https://example.com/logo");

        OrganisationResponseDto responseDto = organisationMapper.toOrganisationResponseDto(organisation);

        assertEquals("Test Organisation", responseDto.getName());
        assertEquals("Test Description pl", responseDto.getDescriptionPL());
        assertEquals("Test Description en", responseDto.getDescriptionEN());
        assertEquals("Test Description ua", responseDto.getDescriptionUA());
        assertEquals("Test Description ru", responseDto.getDescriptionRU());
        assertEquals("test@example.com", responseDto.getEmail());
        assertEquals("123456789", responseDto.getPhoneNumber());
        assertEquals("Test Street", responseDto.getStreet());
        assertEquals("123", responseDto.getHomeNum());
        assertEquals("https://example.com/logo", responseDto.getLogoUrl());
    }

    @Test
    public void testOrganisationResponseAdminDto() {
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Sample Organisation");
        organisation.setDescriptionPL("Description PL");
        organisation.setDescriptionEN("Description EN");
        organisation.setDescriptionUA("Description UA");
        organisation.setDescriptionRU("Description RU");
        organisation.setEmail("sample@example.com");
        organisation.setPhoneNumber("123456789");
        var city = City.builder().id(1L).build();
        var district = District.builder()
                .id(1L)
                .city(city)
                .build();
        var address = Address.builder()
                .street("Test Street")
                .homeNum("123")
                .district(district)
                .build();
        organisation.setAddress(address);
        organisation.setLogoUrl("https://example.com/logo");
        organisation.setApproved(true);

        OrganisationResponseAdminDto responseAdminDto = organisationMapper.organisationResponseAdminDto(organisation);

        assertEquals(1L, responseAdminDto.getId());
        assertEquals("Sample Organisation", responseAdminDto.getName());
        assertEquals("Description PL", responseAdminDto.getDescriptionPL());
        assertEquals("Description EN", responseAdminDto.getDescriptionEN());
        assertEquals("Description UA", responseAdminDto.getDescriptionUA());
        assertEquals("Description RU", responseAdminDto.getDescriptionRU());
        assertEquals("sample@example.com", responseAdminDto.getEmail());
        assertEquals("123456789", responseAdminDto.getPhoneNumber());
        assertEquals("Test Street", responseAdminDto.getStreet());
        assertEquals("123", responseAdminDto.getHomeNum());
        assertEquals("https://example.com/logo", responseAdminDto.getLogoUrl());
        assertTrue(responseAdminDto.isApproved());
    }

    @Test
    public void testToOrganisation() {
        OrganisationRequestDto requestDto = new OrganisationRequestDto();
        requestDto.setName("Test Organisation");
        requestDto.setEmail("test@example.com");
        requestDto.setPhoneNumber("123456789");
        requestDto.setLogoUrl("https://example.com/logo");

        var translationResponce = new OrganisationTranslationResponce();
        translationResponce.setDescriptionPL("Test Description pl");
        translationResponce.setDescriptionEN("Test Description en");
        translationResponce.setDescriptionUA("Test Description ua");
        translationResponce.setDescriptionRU("Test Description ru");

        Organisation organisation = organisationMapper.toOrganisation(requestDto, translationResponce).build();

        assertEquals("Test Organisation", organisation.getName());
        assertEquals("Test Description pl", organisation.getDescriptionPL());
        assertEquals("Test Description en", organisation.getDescriptionEN());
        assertEquals("Test Description ua", organisation.getDescriptionUA());
        assertEquals("Test Description ru", organisation.getDescriptionRU());
        assertEquals("test@example.com", organisation.getEmail());
        assertEquals("123456789", organisation.getPhoneNumber());
        assertTrue(organisation.isApproved());
        assertEquals("https://example.com/logo", organisation.getLogoUrl());
    }

    @Test
    public void testToAddress() {
        OrganisationRequestDto requestDto = new OrganisationRequestDto();
        requestDto.setStreet("Test Street");
        requestDto.setHomeNum("123");

        Address address = organisationMapper.toAddress(requestDto).build();

        assertEquals("Test Street", address.getStreet());
        assertEquals("123", address.getHomeNum());
    }
}
