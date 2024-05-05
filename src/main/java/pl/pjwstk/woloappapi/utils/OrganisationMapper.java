package pl.pjwstk.woloappapi.utils;

import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.OrganisationResponseAdminDto;
import pl.pjwstk.woloappapi.model.entities.Address;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.model.OrganisationRequestDto;
import pl.pjwstk.woloappapi.model.OrganisationResponseDto;

@Component
public class OrganisationMapper {
    public OrganisationResponseDto toOrganisationResponseDto(Organisation organisation) {
        return OrganisationResponseDto.builder()
                .name(organisation.getName())
                .description(organisation.getDescription())
                .email(organisation.getEmail())
                .phoneNumber(organisation.getPhoneNumber())
                .street(organisation.getAddress().getStreet())
                .homeNum(organisation.getAddress().getHomeNum())
                .logoUrl(organisation.getLogoUrl())
                .build();
    }

    public OrganisationResponseAdminDto organisationResponseAdminDto(Organisation organisation) {
        return OrganisationResponseAdminDto.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .description(organisation.getDescription())
                .email(organisation.getEmail())
                .phoneNumber(organisation.getPhoneNumber())
                .street(organisation.getAddress().getStreet())
                .homeNum(organisation.getAddress().getHomeNum())
                .logoUrl(organisation.getLogoUrl())
                .isApproved(organisation.isApproved())
                .build();
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
