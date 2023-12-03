package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.Address;
import pl.pjwstk.woloappapi.model.Organisation;
import pl.pjwstk.woloappapi.model.OrganisationResponseDto;

@Mapper(componentModel = "spring")
public interface OrganisationMapper {
    default OrganisationResponseDto toOrganisationResponseDto(Organisation organisation) {
        OrganisationResponseDto organisationResponseDto = new OrganisationResponseDto();
        organisationResponseDto.setName(organisation.getName());
        organisationResponseDto.setDescription(organisation.getDescription());
        organisationResponseDto.setEmail(organisation.getEmail());
        organisationResponseDto.setPhoneNumber(organisation.getPhoneNumber());
        Address address = organisation.getAddress();
        organisationResponseDto.setStreet(address.getStreet());
        organisationResponseDto.setHomeNum(address.getHomeNum());
        organisationResponseDto.setAddressDescription(address.getAddressDescription());
        organisationResponseDto.setLogoUrl(organisation.getLogoUrl());
        return organisationResponseDto;
    }


}
