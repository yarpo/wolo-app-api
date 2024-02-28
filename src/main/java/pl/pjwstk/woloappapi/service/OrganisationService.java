package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void createOrganisation(OrganisationRequestDto organisationDto) {
        District district = districtService.getDistrictById(organisationDto.getDistrictId());
        Address address = organisationMapper.toAddress(organisationDto).build();
        Organisation organisation = organisationMapper.toOrganisation(organisationDto).build();
        address.setDistrict(district);
        organisation.setAddress(address);
        Optional<UserEntity> user = userRepository.findById(organisationDto.getModeratorId());
        user.ifPresent(organisation::setModerator);
        organisationRepository.save(organisation);
    }

    @Transactional
    public void updateOrganisation(OrganisationRequestDto organisationDto, Long id) {
        Organisation organisation =organisationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Organisation with ID " + id + " does not exist"));
        organisation.setName(organisationDto.getName());
        organisation.setDescription(organisationDto.getDescription());
        organisation.setEmail(organisationDto.getEmail());
        organisation.setPhoneNumber(organisationDto.getPhoneNumber());
        organisation.setApproved(false);
        organisation.setLogoUrl(organisationDto.getLogoUrl());

        Address address = organisation.getAddress();
        address.setStreet(organisationDto.getStreet());
        address.setHomeNum(organisationDto.getHomeNum());
        address.setDistrict(districtService.getDistrictById(organisationDto.getDistrictId()));
        organisation.setAddress(address);
        organisation.setLogoUrl(organisationDto.getLogoUrl());
        organisationRepository.save(organisation);
    }

    public List<Event> getEventsByOrganisation(Long id) {
        return organisationRepository
                .findById(id)
                .map(Organisation::getEvents)
                .orElseThrow(() -> new NotFoundException("Organizer id not found!"));
    }
}
