package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import java.util.List;
import java.util.Objects;
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

    public void updateOrganisation(OrganisationRequestDto organisationDto, Long id) {
        Organisation organisation = organisationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Organisation with ID " + id + " does not exist"));
        if(!Objects.equals(organisation.getName(), organisationDto.getName())){
            organisation.setName(organisationDto.getName());
        }

        if(!Objects.equals(organisation.getDescription(), organisationDto.getDescription())){
            organisation.setDescription(organisationDto.getDescription());
        }

        if(!organisation.getEmail().equals(organisationDto.getEmail())){
            organisation.setEmail(organisationDto.getEmail());
        }

        if(!Objects.equals(organisation.getPhoneNumber(), organisationDto.getPhoneNumber())){
            organisation.setPhoneNumber(organisationDto.getPhoneNumber());
        }

        if(!Objects.equals(organisation.getAddress().getStreet(), organisationDto.getStreet())){
            organisation.getAddress().setStreet(organisationDto.getStreet());
        }

        if(!Objects.equals(organisation.getAddress().getHomeNum(), organisationDto.getHomeNum())){
            organisation.getAddress().setHomeNum(organisationDto.getHomeNum());
        }

        if(!Objects.equals(organisation.getAddress().getAddressDescription(),
                organisationDto.getAddressDescription())){
            organisation.getAddress().setAddressDescription(organisationDto.getAddressDescription());
        }

        if(!Objects.equals(organisation.getAddress().getDistrict().getId(), organisationDto.getDistrictId())){
            District district = districtService.getDistrictById(organisationDto.getDistrictId());
            organisation.getAddress().setDistrict(district);
        }

        if(!Objects.equals(organisation.getModerator().getId(), organisationDto.getModeratorId())){
            userRepository.findById(organisationDto.getModeratorId()).ifPresent(organisation::setModerator);
        }

        if(!Objects.equals(organisation.getLogoUrl(), organisationDto.getLogoUrl())){
            organisation.setLogoUrl(organisationDto.getLogoUrl());
        }

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
