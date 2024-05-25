package pl.pjwstk.woloappapi.utils;

import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.admin.OrganisationResponseAdminDto;
import pl.pjwstk.woloappapi.model.entities.Address;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.model.translation.OrganisationTranslationResponce;

@Component
public class OrganisationMapper {
    public OrganisationResponseDto toOrganisationResponseDto(Organisation organisation) {
        return OrganisationResponseDto.builder()
                .name(organisation.getName())
                .descriptionPL(organisation.getDescriptionPL())
                .descriptionEN(organisation.getDescriptionEN())
                .descriptionUA(organisation.getDescriptionUA())
                .descriptionRU(organisation.getDescriptionRU())
                .email(organisation.getEmail())
                .phoneNumber(organisation.getPhoneNumber())
                .street(organisation.getAddress().getStreet())
                .homeNum(organisation.getAddress().getHomeNum())
                .cityId(organisation.getAddress().getDistrict().getCity().getId())
                .districtId(organisation.getAddress().getDistrict().getId())
                .logoUrl(organisation.getLogoUrl())
                .build();
    }

    public OrganisationResponseAdminDto organisationResponseAdminDto(Organisation organisation) {
        return OrganisationResponseAdminDto.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .descriptionPL(organisation.getDescriptionPL())
                .descriptionEN(organisation.getDescriptionEN())
                .descriptionUA(organisation.getDescriptionUA())
                .descriptionRU(organisation.getDescriptionRU())
                .email(organisation.getEmail())
                .phoneNumber(organisation.getPhoneNumber())
                .street(organisation.getAddress().getStreet())
                .homeNum(organisation.getAddress().getHomeNum())
                .cityId(organisation.getAddress().getDistrict().getCity().getId())
                .districtId(organisation.getAddress().getDistrict().getId())
                .logoUrl(organisation.getLogoUrl())
                .isApproved(organisation.isApproved())
                .build();
    }

    public Organisation.OrganisationBuilder toOrganisation(OrganisationRequestDto organisationRequestDto,
                                                           OrganisationTranslationResponce translation){
        return Organisation.builder()
                .name(organisationRequestDto.getName())
                .descriptionPL(translation.getDescriptionPL())
                .descriptionEN(translation.getDescriptionEN())
                .descriptionUA(translation.getDescriptionUA())
                .descriptionRU(translation.getDescriptionRU())
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
