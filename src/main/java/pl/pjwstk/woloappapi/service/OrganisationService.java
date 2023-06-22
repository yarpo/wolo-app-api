package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.utils.EventNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganisationService {
    private final OrganisationRepository organisationRepository;
    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public Organisation getOrganisationById(Long id) {
        return organisationRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Organisation id not found!"));
    }

    public void createOrganisation(Organisation organisation) {
        organisationRepository.save(organisation);
    }

    public Organisation updateOrganisation(Organisation organisation) {
        if (!organisationRepository.existsById(organisation.getId())) {
            throw new IllegalArgumentException("Organisation with ID " + organisation.getId() + " does not exist");
        }
        return organisationRepository.save(organisation);
    }

    public void deleteOrganisation(Long id) {
        if (!organisationRepository.existsById(id)) {
            throw new IllegalArgumentException("Organisation with ID " + id + " does not exist");
        }
        organisationRepository.deleteById(id);
    }


}
