package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.pjwstk.woloappapi.controller.OrganisationController;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.service.OrganisationService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrganisationControllerTest {

    @Mock
    private OrganisationService organisationService;

    @InjectMocks
    private OrganisationController organisationController;

    @Test
    public void testGetOrganisations() {
        List<Organisation> organisations = new ArrayList<>();
        organisations.add(createOrganisation(1L, "Organisation 1", "Description 1",
                "org1@gmail.com", "123456789", true));
        organisations.add(createOrganisation(2L, "Organisation 2", "Description 2",
                "org2@gmail.com", "987654321", true));
        when(organisationService.getAllOrganisations()).thenReturn(organisations);

        ResponseEntity<List<Organisation>> responseEntity = organisationController.getOrganisations();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(organisations, responseEntity.getBody());
        verify(organisationService, times(1)).getAllOrganisations();
    }

    @Test
    public void testGetOrganisationById_ExistingId() {
        Long organisationId = 1L;
        Organisation organisation = createOrganisation(organisationId, "Organisation 1", "Description 1",
                "org1@gmail.com", "123456789", true);
        when(organisationService.getOrganisationById(organisationId)).thenReturn(organisation);

        ResponseEntity<Organisation> responseEntity = organisationController.getOrganisationById(organisationId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(organisation, responseEntity.getBody());
        verify(organisationService, times(1)).getOrganisationById(organisationId);
    }

    @Test
    public void testAddOrganisation_ValidOrganisation() {
        Organisation organisationToAdd = createOrganisation(1L, "Organisation 1", "Description 1",
                "org1@gmail.com", "123456789", true);

        ResponseEntity<HttpStatus> responseEntity = organisationController.addOrganisation(organisationToAdd);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(organisationService, times(1)).createOrganisation(organisationToAdd);
    }


    private Organisation createOrganisation(Long id, String name, String description, String email,
                                            String phoneNumber, boolean isApproved) {
        Organisation organisation = new Organisation();
        organisation.setId(id);
        organisation.setName(name);
        organisation.setDescription(description);
        organisation.setEmail(email);
        organisation.setPhoneNumber(phoneNumber);
        organisation.setApproved(isApproved);
        return organisation;
    }
}
