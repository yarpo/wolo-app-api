package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.OrganisationEditRequestDto;
import pl.pjwstk.woloappapi.model.OrganisationRequestDto;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.model.translation.OrganisationTranslationResponce;
import pl.pjwstk.woloappapi.repository.AddressRepository;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class OrganisationServiceTests {

    @Mock
    private OrganisationRepository organisationRepository;

    @Mock
    private OrganisationMapper organisationMapper;

    @Mock
    private DistrictService districtService;

    @Mock
    private RoleService roleService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

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
    public void testCreateOrganisation() {
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

        var district = District.builder().id(1L).build();
        var role = Role.builder().id(1L).name("MODERATOR").build();
        var address = Address.builder()
                .street(organisationRequestDto.getStreet())
                .homeNum(organisationRequestDto.getHomeNum())
                .build();
        when(addressRepository.save(any())).thenReturn(address);
        when(districtService.getDistrictById(1L)).thenReturn(district);
        when(organisationMapper.toAddress(organisationRequestDto)).thenReturn(address.builder());
        when(roleService.getRoleByName(anyString())).thenReturn(role);
        when(organisationMapper.toOrganisation(organisationRequestDto, translationResponce)).thenReturn(Organisation.builder()
                .name(organisationRequestDto.getName())
                .descriptionPL(translationResponce.getDescriptionPL())
                .email(organisationRequestDto.getEmail())
                .phoneNumber(organisationRequestDto.getPhoneNumber())
                .address(address)
                .isApproved(true)
                .logoUrl(organisationRequestDto.getLogoUrl()));

        User user = new User();
        user.setId(1L);
        user.setRoles(new ArrayList<>());
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

        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateCategory() {
        OrganisationEditRequestDto organisationRequestDto = new OrganisationEditRequestDto();
        organisationRequestDto.setName("New Organisation Name");

        Long organisationId = 1L;

        Organisation organisation = new Organisation();
        organisation.setId(organisationId);
        organisation.setName("Old Organisation Name");

        Address address = new Address();
        organisation.setAddress(address);

        when(organisationRepository.findById(1L)).thenReturn(Optional.of(organisation));

        organisationService.updateOrganisation(organisationRequestDto, organisationId);

        verify(organisationRepository, times(1)).save(organisation);
        assertEquals("New Organisation Name", organisation.getName());
    }

    @Test
    void testGetEventsByOrganisation() {
        Long organisationId = 1L;
        Organisation organisation = new Organisation();
        List<Event> events = List.of(new Event(), new Event());
        organisation.setEvents(events);

        when(organisationRepository.findById(organisationId)).thenReturn(Optional.of(organisation));

        List<Event> result = organisationService.getEventsByOrganisation(organisationId);

        assertEquals(events.size(), result.size());
        assertEquals(events, result);
        verify(organisationRepository, times(1)).findById(organisationId);
    }

    @Test
    void testGetPastEventsByOrganisation() {
        Long organisationId = 1L;
        Organisation organisation = new Organisation();

        Event pastEvent = new Event();
        pastEvent.setDate(LocalDate.now().minusDays(1));
        Shift pastShift = new Shift();
        pastShift.setEvent(pastEvent);
        pastEvent.setShifts(List.of(pastShift));

        Event futureEvent = new Event();
        futureEvent.setDate(LocalDate.now().plusDays(1));
        Shift futureShift = new Shift();
        futureShift.setEvent(futureEvent);
        futureEvent.setShifts(List.of(futureShift));

        organisation.setEvents(List.of(pastEvent, futureEvent));

        when(organisationRepository.findById(organisationId)).thenReturn(Optional.of(organisation));

        List<Event> result = organisationService.getPastEventsByOrganisation(organisationId);

        assertEquals(1, result.size());
        assertEquals(pastEvent, result.get(0));
        verify(organisationRepository, times(1)).findById(organisationId);
    }

    @Test
    void testGetFutureAndNowEventsByOrganisation() {
        Long organisationId = 1L;
        Organisation organisation = new Organisation();

        Event pastEvent = new Event();
        pastEvent.setDate(LocalDate.now().minusDays(1));
        Shift pastShift = new Shift();
        pastShift.setEvent(pastEvent);
        pastEvent.setShifts(List.of(pastShift));

        Event futureEvent = new Event();
        futureEvent.setDate(LocalDate.now().plusDays(1));
        Shift futureShift = new Shift();
        futureShift.setEvent(futureEvent);
        futureEvent.setShifts(List.of(futureShift));

        Event nowEvent = new Event();
        nowEvent.setDate(LocalDate.now());
        Shift nowShift = new Shift();
        nowShift.setEvent(nowEvent);
        nowEvent.setShifts(List.of(nowShift));

        organisation.setEvents(List.of(pastEvent, futureEvent, nowEvent));

        when(organisationRepository.findById(organisationId)).thenReturn(Optional.of(organisation));

        List<Event> result = organisationService.getFutureAndNowEventsByOrganisation(organisationId);

        assertEquals(2, result.size());
        assertTrue(result.contains(futureEvent));
        assertTrue(result.contains(nowEvent));
        verify(organisationRepository, times(1)).findById(organisationId);
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
