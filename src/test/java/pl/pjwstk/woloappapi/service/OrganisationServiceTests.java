package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.model.translation.OrganisationTranslationResponce;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class OrganisationServiceTests {

    @Mock
    private DistrictService districtService;

    @Mock
    private OrganisationMapper organisationMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganisationRepository organisationRepository;

    @InjectMocks
    private OrganisationService organisationService;

    @Test
    public void testGetAllOrganisations(){
        Organisation organisation1 = new Organisation();
        Organisation organisation2 = new Organisation();

        when(organisationRepository.findAll()).thenReturn(Arrays.asList(organisation1, organisation2));

        List<Organisation> actualRoles = organisationService.getAllOrganisations();

        assertEquals(2, actualRoles.size());

    }

    @Test
    public void testGetByOrganisationId(){
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        when(organisationRepository.findById(1L)).thenReturn(Optional.of(organisation));

        Organisation retrievedOrganisation = organisationService.getOrganisationById(1L);

        assertEquals(organisation.getId(), retrievedOrganisation.getId());
    }

    @Test
    public void testCreateOrganisation(){
        OrganisationRequestDto organisationRequestDto = new OrganisationRequestDto();
        organisationRequestDto.setName("Test Name");
        organisationRequestDto.setDescription("Test Description");
        organisationRequestDto.setEmail("test@example.com");
        organisationRequestDto.setPhoneNumber("123456789");
        organisationRequestDto.setStreet("Test Street");
        organisationRequestDto.setHomeNum("123");
        organisationRequestDto.setDistrictId(1L);
        organisationRequestDto.setModeratorId(1L);
        organisationRequestDto.setLogoUrl("http://example.com/logo");

        var translationResponce = new OrganisationTranslationResponce();
        translationResponce.setDescriptionPL("Test Description pl");
        translationResponce.setDescriptionEN("Test Description en");
        translationResponce.setDescriptionUA("Test Description ua");
        translationResponce.setDescriptionRU("Test Description ru");

        when(organisationMapper.toAddress(organisationRequestDto)).thenReturn(Address.builder()
                .street(organisationRequestDto.getStreet())
                .homeNum(organisationRequestDto.getHomeNum()));


        when(organisationMapper.toOrganisation(organisationRequestDto, translationResponce)).thenReturn(Organisation.builder()
                .name(organisationRequestDto.getName())
                .descriptionPL(translationResponce.getDescriptionPL())
                .email(organisationRequestDto.getEmail())
                .phoneNumber(organisationRequestDto.getPhoneNumber())
                .isApproved(true)
                .logoUrl(organisationRequestDto.getLogoUrl()));

        District district = new District();
        district.setId(1L);
        when(districtService.getDistrictById(1L)).thenReturn(district);

        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        organisationService.createOrganisation(translationResponce, organisationRequestDto);

        ArgumentCaptor<Organisation> organisationCaptor = ArgumentCaptor.forClass(Organisation.class);
        verify(organisationRepository).save(organisationCaptor.capture());
        Organisation capturedOrganisation = organisationCaptor.getValue();
        assertEquals("Test Name", capturedOrganisation.getName());
        assertEquals("Test Description pl", capturedOrganisation.getDescriptionPL());
        assertEquals("test@example.com", capturedOrganisation.getEmail());
        assertEquals("123456789", capturedOrganisation.getPhoneNumber());
        assertEquals("Test Street", capturedOrganisation.getAddress().getStreet());
        assertEquals("123", capturedOrganisation.getAddress().getHomeNum());
        assertEquals(1L, capturedOrganisation.getAddress().getDistrict().getId());
        assertEquals(1L, capturedOrganisation.getModerator().getId());
        assertTrue(capturedOrganisation.isApproved());
        assertEquals("http://example.com/logo", capturedOrganisation.getLogoUrl());

    }

    @Test
    public void testUpdateCategory() {
        var translationResponce = new OrganisationTranslationResponce();
        translationResponce.setDescriptionPL("Test Description pl");
        translationResponce.setDescriptionEN("Test Description en");
        translationResponce.setDescriptionUA("Test Description ua");
        translationResponce.setDescriptionRU("Test Description ru");

        OrganisationRequestDto organisationRequestDto = new OrganisationRequestDto();
        organisationRequestDto.setName("New Organisation Name");

        Long organisationId = 1L;

        Organisation organisation = new Organisation();
        organisation.setId(organisationId);
        organisation.setName("Old Organisation Name");

        Address address = new Address();
        organisation.setAddress(address);

        when(organisationRepository.findById(1L)).thenReturn(Optional.of(organisation));

        organisationService.updateOrganisation(organisationRequestDto, organisationId, translationResponce);

        verify(organisationRepository, times(1)).save(organisation);
        assertEquals("New Organisation Name", organisation.getName());
    }

    @Test
    public void testGetEventsByOrganisation() {
        Organisation organisation = new Organisation();
        organisation.setId(1L);

        List<Event> events = new ArrayList<>();
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(2L);
        organisation.setEvents(events);

        when(organisationRepository.findById(1L)).thenReturn(Optional.of(organisation));

        List<Event> eventsByOrganisation = organisationService.getEventsByOrganisation(1L);

        verify(organisationRepository, times(1)).findById(1L);

        assertEquals(events, eventsByOrganisation);
    }

    @Test
    public void testApprove() {
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setApproved(false);

        when(organisationRepository.findById(1L)).thenReturn(Optional.of(organisation));

        organisationService.approve(1L);

        assertTrue(organisation.isApproved());
        verify(organisationRepository, times(1)).findById(1L);
    }


    @Test
    public void testDisapprove() {
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setApproved(true);

        when(organisationRepository.findById(1L)).thenReturn(Optional.of(organisation));

        organisationService.disapprove(1L);

        assertFalse(organisation.isApproved());
        verify(organisationRepository, times(1)).findById(1L);
    }


}
