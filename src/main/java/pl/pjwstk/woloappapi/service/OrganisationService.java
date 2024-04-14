package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.OrganisationRequestDto;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.repository.AddressRepository;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final OrganisationMapper organisationMapper;
    private final DistrictService districtService;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public Organisation getOrganisationById(Long id) {
        return organisationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organisation id not found!"));
    }
    @Transactional
    public void createOrganisation(OrganisationRequestDto organisationDto) {
        var district = districtService.getDistrictById(organisationDto.getDistrictId());
        var user = userRepository.findById(organisationDto.getModeratorId())
                .orElseThrow(()-> new NotFoundException("User id not found!"));
        var address = addressRepository.save(organisationMapper.toAddress(organisationDto)
                .district(district)
                .build());
        var organisation = organisationMapper.toOrganisation(organisationDto)
                .moderator(user)
                .address(address)
                .build();
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
        organisation.setLogoUrl(organisationDto.getLogoUrl());

        var address = organisation.getAddress();
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

    public void approve(Long organisationId) {
        var organisation = organisationRepository.findById(organisationId);
        organisation.ifPresent(o -> o.setApproved(true));
    }

    public void disapprove(Long organisationId) {
        var organisation = organisationRepository.findById(organisationId);
        organisation.ifPresent(o -> o.setApproved(false));
    }
}
