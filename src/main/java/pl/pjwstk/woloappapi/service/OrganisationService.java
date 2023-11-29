package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final OrganisationMapper organisationMapper;
    private final DistrictService districtService;
    private final UserRepository userRepository;
    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public Organisation getOrganisationById(Long id) {
        return organisationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organisation id not found!"));
    }

    public void createOrganisation(OrganisationRequestDto organisationDto) {
        District district = districtService.getDistrictById(organisationDto.getDistrictId());
        Address address = organisationMapper.INSTANCE.toAddress(organisationDto);
        Organisation organisation = organisationMapper.INSTANCE.toOrganisation(organisationDto);
        address.setDistrict(district);
        organisation.setAddress(address);
        Optional<User> user = userRepository.findById(organisationDto.getModeratorId());
        user.ifPresent(organisation::setModerator);
        organisationRepository.save(organisation);
    }

    public void updateOrganisation(Organisation organisation, Long id) {
        if (!organisationRepository.existsById(id)) {
            throw new IllegalArgumentException("Organisation with ID " + id + " does not exist");
        }
        organisation.setId(id);
        organisationRepository.save(organisation);
    }

    public void deleteOrganisation(Long id) {
        if (!organisationRepository.existsById(id)) {
            throw new IllegalArgumentException("Organisation with ID " + id + " does not exist");
        }
        organisationRepository.deleteById(id);
    }

    public List<Event> getEventsByOrganisation(Long id) {
        return organisationRepository.findById(id)
                .map(Organisation::getEvents)
                .orElseThrow(() -> new NotFoundException("Organizer id not found!"));
    }


}
