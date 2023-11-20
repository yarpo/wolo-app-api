package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.pjwstk.woloappapi.model.Address;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.service.OrganisationService;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrganisationServiceTest {

    @Mock
    private OrganisationRepository organisationRepository;

    @InjectMocks
    private OrganisationService organisationService;

    @Test
    public void testGetAllOrganisations() {
        List<Organisation> organisations = new ArrayList<>();
        organisations.add(createValidOrganisation(1L, "Organisation 1", "org1@gmail.com"));
        organisations.add(createValidOrganisation(1L, "Organisation 2", "org2@gmail.com"));
        when(organisationRepository.findAll()).thenReturn(organisations);

        List<Organisation> result = organisationService.getAllOrganisations();

        assertEquals(organisations, result);
        verify(organisationRepository, times(1)).findAll();

    }

    @Test
    public void testGetOrganisationById_ExistingId() {
        Long organisationId = 1L;
        Organisation organisation = createValidOrganisation(organisationId, "Organisation 1", "org1@gmail.com");
        when(organisationRepository.findById(organisationId)).thenReturn(Optional.of(organisation));

        Organisation result = organisationService.getOrganisationById(organisationId);

        assertEquals(organisation, result);
        verify(organisationRepository, times(1)).findById(organisationId);
    }

    @Test
    public void testGetOrganisationById_NonExistingId() {
        Long organisationId = 1L;
        when(organisationRepository.findById(organisationId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> organisationService.getOrganisationById(organisationId));
        verify(organisationRepository, times(1)).findById(organisationId);
    }

    @Test
    public void testCreateOrganisation_ValidOrganisation() {
        Organisation organisation = createValidOrganisation(1L, "Organisation 1", "org1@gmail.com");

        organisationService.createOrganisation(organisation);

        verify(organisationRepository, times(1)).save(organisation);
    }

    @Test
    public void testDeleteOrganisation_ExistingId() {
        Long organisationId = 1L;
        when(organisationRepository.existsById(organisationId)).thenReturn(true);

        organisationService.deleteOrganisation(organisationId);

        verify(organisationRepository, times(1)).existsById(organisationId);
        verify(organisationRepository, times(1)).deleteById(organisationId);
    }

    @Test
    public void testDeleteOrganisation_NonExistingId() {
        Long organisationId = 1L;
        when(organisationRepository.existsById(organisationId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> organisationService.deleteOrganisation(organisationId));
        verify(organisationRepository, times(1)).existsById(organisationId);
        verify(organisationRepository, never()).deleteById(organisationId);
    }

    private Organisation createValidOrganisation(Long id, String name, String email) {
        Organisation organisation = new Organisation();
        organisation.setId(id);
        organisation.setName(name);
        organisation.setDescription("Organisation Description");
        organisation.setEmail(email);
        organisation.setPhoneNumber("123456789");
        organisation.setAddress(new Address());
        organisation.setApproved(true);
        organisation.setModerator(new User());
        return organisation;
    }
}
