package pl.pjwstk.woloappapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.OrganisationRequestDto;
import pl.pjwstk.woloappapi.model.OrganisationResponseDto;
import pl.pjwstk.woloappapi.model.translation.OrganisationTranslationResponce;
import pl.pjwstk.woloappapi.model.entities.Address;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class OrganisationMapperTests {

    OrganisationMapper organisationMapper = new OrganisationMapper();

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
        Address address = new Address();
        address.setStreet("Test Street");
        address.setHomeNum("123");
        organisation.setAddress(address);
        organisation.setLogoUrl("http://example.com/logo");

        OrganisationResponseDto responseDto = organisationMapper.toOrganisationResponseDto(organisation);

        assertEquals("Test Organisation", responseDto.getName());
        assertEquals("Test Description pl", responseDto.getDescriptionPL());
        assertEquals("test@example.com", responseDto.getEmail());
        assertEquals("123456789", responseDto.getPhoneNumber());
        assertEquals("Test Street", responseDto.getStreet());
        assertEquals("123", responseDto.getHomeNum());
        assertEquals("http://example.com/logo", responseDto.getLogoUrl());
    }

    @Test
    public void testToOrganisation() {
        OrganisationRequestDto requestDto = new OrganisationRequestDto();
        requestDto.setName("Test Organisation");
        requestDto.setDescription("Test Description");
        requestDto.setEmail("test@example.com");
        requestDto.setPhoneNumber("123456789");
        requestDto.setLogoUrl("http://example.com/logo");

        var translationResponce = new OrganisationTranslationResponce();
        translationResponce.setDescriptionPL("Test Description pl");
        translationResponce.setDescriptionEN("Test Description en");
        translationResponce.setDescriptionUA("Test Description ua");
        translationResponce.setDescriptionRU("Test Description ru");

        Organisation organisation = organisationMapper.toOrganisation(requestDto, translationResponce).build();

        assertEquals("Test Organisation", organisation.getName());
        assertEquals("Test Description pl", organisation.getDescriptionPL());
        assertEquals("test@example.com", organisation.getEmail());
        assertEquals("123456789", organisation.getPhoneNumber());
        assertTrue(organisation.isApproved());
        assertEquals("http://example.com/logo", organisation.getLogoUrl());
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
