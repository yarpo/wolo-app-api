package pl.pjwstk.woloappapi.utils;

import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.entities.Address;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.model.OrganisationRequestDto;
import pl.pjwstk.woloappapi.model.OrganisationResponseDto;

@Component
public class OrganisationMapper {
    public OrganisationResponseDto toOrganisationResponseDto(Organisation organisation) {
        OrganisationResponseDto organisationResponseDto = new OrganisationResponseDto();
        organisationResponseDto.setName(organisation.getName());
        organisationResponseDto.setDescription(organisation.getDescription());
        organisationResponseDto.setEmail(organisation.getEmail());
        organisationResponseDto.setPhoneNumber(organisation.getPhoneNumber());
        Address address = organisation.getAddress();
        organisationResponseDto.setStreet(address.getStreet());
        organisationResponseDto.setHomeNum(address.getHomeNum());
        organisationResponseDto.setLogoUrl(organisation.getLogoUrl());
        return organisationResponseDto;
    }
    public Organisation.OrganisationBuilder toOrganisation(OrganisationRequestDto organisationRequestDto){
        return Organisation.builder()
                .name(organisationRequestDto.getName())
                .description(organisationRequestDto.getDescription())
                .email(organisationRequestDto.getEmail())
                .phoneNumber(organisationRequestDto.getPhoneNumber())
                .isApproved(true)
                .logoUrl(organisationRequestDto.getLogoUrl());
    }

    public Address.AddressBuilder toAddress(OrganisationRequestDto organisationRequestDto) {
        return Address.builder()
                .street(organisationRequestDto.getStreet())
                .homeNum(organisationRequestDto.getHomeNum());
    }
}
