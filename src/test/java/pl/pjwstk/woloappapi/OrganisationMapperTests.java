package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.Address;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.model.OrganisationRequestDto;
import pl.pjwstk.woloappapi.model.OrganisationResponseDto;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@ExtendWith(MockitoExtension.class)
public class OrganisationMapperTests {
    private OrganisationMapper createOrganisationMapper() {
        return Mappers.getMapper(OrganisationMapper.class);
    }

    @Test
    public void testToOrganisationResponseDto() {
        OrganisationMapper organisationMapper = createOrganisationMapper();
        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescription("Test Description");
        organisation.setEmail("test@example.com");
        organisation.setPhoneNumber("123456789");
        Address address = new Address();
        address.setStreet("Test Street");
        address.setHomeNum("123");
        address.setAddressDescription("Test Address");
        organisation.setAddress(address);
        organisation.setLogoUrl("http://example.com/logo");

        OrganisationResponseDto responseDto = organisationMapper.toOrganisationResponseDto(organisation);

        assertEquals("Test Organisation", responseDto.getName());
        assertEquals("Test Description", responseDto.getDescription());
        assertEquals("test@example.com", responseDto.getEmail());
        assertEquals("123456789", responseDto.getPhoneNumber());
        assertEquals("Test Street", responseDto.getStreet());
        assertEquals("123", responseDto.getHomeNum());
        assertEquals("Test Address", responseDto.getAddressDescription());
        assertEquals("http://example.com/logo", responseDto.getLogoUrl());
    }

    @Test
    public void testToOrganisation() {
        OrganisationMapper organisationMapper = createOrganisationMapper();
        OrganisationRequestDto requestDto = new OrganisationRequestDto();
        requestDto.setName("Test Organisation");
        requestDto.setDescription("Test Description");
        requestDto.setEmail("test@example.com");
        requestDto.setPhoneNumber("123456789");
        requestDto.setLogoUrl("http://example.com/logo");

        Organisation organisation = organisationMapper.toOrganisation(requestDto);

        assertEquals("Test Organisation", organisation.getName());
        assertEquals("Test Description", organisation.getDescription());
        assertEquals("test@example.com", organisation.getEmail());
        assertEquals("123456789", organisation.getPhoneNumber());
        assertFalse(organisation.isApproved());
        assertEquals("http://example.com/logo", organisation.getLogoUrl());
    }


    @Test
    public void testToAddress() {
        OrganisationMapper organisationMapper = createOrganisationMapper();
        OrganisationRequestDto requestDto = new OrganisationRequestDto();
        requestDto.setStreet("Test Street");
        requestDto.setHomeNum("123");
        requestDto.setAddressDescription("Test Address");

        Address address = organisationMapper.toAddress(requestDto);

        assertEquals("Test Street", address.getStreet());
        assertEquals("123", address.getHomeNum());
        assertEquals("Test Address", address.getAddressDescription());
    }
}
