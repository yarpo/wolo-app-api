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
import java.util.function.Consumer;
import java.util.function.Supplier;

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
        return organisationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Organisation id not found!"));
    }

    public void createOrganisation(OrganisationRequestDto organisationDto) {
        District district = districtService.getDistrictById(organisationDto.getDistrictId());
        Address address = organisationMapper.toAddress(organisationDto);
        Organisation organisation = organisationMapper.toOrganisation(organisationDto);
        address.setDistrict(district);
        organisation.setAddress(address);
        Optional<UserEntity> user = userRepository.findById(organisationDto.getModeratorId());
        user.ifPresent(organisation::setModerator);
        organisationRepository.save(organisation);
    }

    public void updateOrganisation(OrganisationRequestDto organisationDto, Long id) {
        Organisation organisation =
                organisationRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "Organisation with ID " + id + " does not exist"));
        Address address = organisation.getAddress();
        updateFieldIfDifferent(
                organisation::getName, organisation::setName, organisationDto.getName());
        updateFieldIfDifferent(
                organisation::getDescription,
                organisation::setDescription,
                organisationDto.getDescription());
        updateFieldIfDifferent(
                organisation::getEmail, organisation::setEmail, organisationDto.getEmail());

        updateFieldIfDifferent(address::getStreet, address::setStreet, organisationDto.getStreet());
        updateFieldIfDifferent(
                address::getHomeNum, address::setHomeNum, organisationDto.getHomeNum());
        updateFieldIfDifferent(
                address::getAddressDescriptionPL,
                address::setAddressDescriptionPL,
                organisationDto.getAddressDescription());
        updateFieldIfDifferent(
                () -> address.getDistrict().getId(),
                dId -> {
                    District district = districtService.getDistrictById(dId);
                    address.setDistrict(district);
                },
                organisationDto.getDistrictId());
        organisation.setAddress(address);

        updateFieldIfDifferent(
                () -> organisation.getModerator().getId(),
                mId -> userRepository.findById(mId).ifPresent(organisation::setModerator),
                organisationDto.getModeratorId());
        updateFieldIfDifferent(
                organisation::getLogoUrl, organisation::setLogoUrl, organisationDto.getLogoUrl());

        organisationRepository.save(organisation);
    }

    public void deleteOrganisation(Long id) {
        if (!organisationRepository.existsById(id)) {
            throw new IllegalArgumentException("Organisation with ID " + id + " does not exist");
        }
        organisationRepository.deleteById(id);
    }

    public List<Event> getEventsByOrganisation(Long id) {
        return organisationRepository
                .findById(id)
                .map(Organisation::getEvents)
                .orElseThrow(() -> new NotFoundException("Organizer id not found!"));
    }

    private <T> void updateFieldIfDifferent(
            Supplier<T> currentSupplier, Consumer<T> updateConsumer, T newValue) {
        if (!Objects.equals(currentSupplier.get(), newValue)) {
            updateConsumer.accept(newValue);
        }
    }
}
