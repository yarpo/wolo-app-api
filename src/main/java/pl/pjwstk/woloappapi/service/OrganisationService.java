package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.OrganisationEditRequestDto;
import pl.pjwstk.woloappapi.model.OrganisationRequestDto;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.model.translation.OrganisationTranslationResponce;
import pl.pjwstk.woloappapi.repository.AddressRepository;
import pl.pjwstk.woloappapi.repository.OrganisationRepository;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.OrganisationMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final OrganisationMapper organisationMapper;
    private final DistrictService districtService;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    private final RoleService roleService;

    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public List<Organisation> getApprovedAllOrganisations() { return organisationRepository.getAllApprovedOrganisations(); }

    public Organisation getOrganisationById(Long id) {
        return organisationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organisation id not found!"));
    }
    @Transactional
    public void createOrganisation(OrganisationTranslationResponce translation,
                                   OrganisationRequestDto organisationDto) {
        var district = districtService.getDistrictById(organisationDto.getDistrictId());
        var user = userRepository.findById(organisationDto.getModeratorId())
                .orElseThrow(()-> new NotFoundException("User id not found!"));
        var mapAddress = organisationMapper.toAddress(organisationDto).build();
        var address = addressRepository.save(mapAddress);
        address.setDistrict(district);
        var organisation = organisationMapper.toOrganisation(organisationDto, translation)
                .moderator(user)
                .address(address)
                .build();
        var saved = organisationRepository.save(organisation);
        user.setOrganisation(saved);
        user.getRoles().add(roleService.getRoleByName("MODERATOR"));
        userRepository.save(user);
    }

    @Transactional
    public void updateOrganisation(OrganisationEditRequestDto organisationDto,
                                   Long id) {
        Organisation organisation =organisationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Organisation with ID " + id + " does not exist"));
        organisation.setName(organisationDto.getName());
        organisation.setDescriptionPL(organisationDto.getDescriptionPL());
        organisation.setDescriptionEN(organisationDto.getDescriptionEN());
        organisation.setDescriptionUA(organisationDto.getDescriptionUA());
        organisation.setDescriptionRU(organisationDto.getDescriptionRU());
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

    public List<Event> getPastEventsByOrganisation(Long id) {
        return organisationRepository
                .findById(id)
                .map(Organisation::getEvents)
                .orElseThrow(() -> new NotFoundException("Organizer id not found!"))
                .stream()
                .filter(event -> event.getShifts()
                        .stream().anyMatch(shift -> shift.getEvent().getDate().isBefore(LocalDate.now())))
                .toList();
    }

    public List<Event> getFutureAndNowEventsByOrganisation(Long id) {
        return organisationRepository
                .findById(id)
                .map(Organisation::getEvents)
                .orElseThrow(() -> new NotFoundException("Organizer id not found!"))
                .stream()
                .filter(event -> event.getShifts()
                        .stream().anyMatch(shift -> !shift.getEvent().getDate().isBefore(LocalDate.now())))
                .toList();
    }

    @Transactional
    public void approve(Long organisationId) {
        var organisation = organisationRepository.findById(organisationId);
        if(organisation.isPresent()) {
            organisation.get().setApproved(true);
            organisationRepository.save(organisation.get());
        }else{
            throw new IllegalArgumentException("Organisation id does not exist");
        }
    }

    @Transactional
    public void disapprove(Long organisationId) {
        var organisation = organisationRepository.findById(organisationId);
        if(organisation.isPresent()) {
            organisation.get().setApproved(false);
            organisationRepository.save(organisation.get());
        }else{
            throw new IllegalArgumentException("Organisation id does not exist");
        }
    }
}
