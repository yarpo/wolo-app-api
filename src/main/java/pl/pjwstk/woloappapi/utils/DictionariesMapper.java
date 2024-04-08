package pl.pjwstk.woloappapi.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.CategoryDto;
import pl.pjwstk.woloappapi.model.CityDto;
import pl.pjwstk.woloappapi.model.DistrictDto;
import pl.pjwstk.woloappapi.model.RoleDto;
import pl.pjwstk.woloappapi.model.entities.Category;
import pl.pjwstk.woloappapi.model.entities.City;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.model.entities.Role;
import pl.pjwstk.woloappapi.service.DistrictService;

@Component
@RequiredArgsConstructor
public class DictionariesMapper {
    private final DistrictService districtService;

    public DistrictDto toDistrictDto(District district){
        DistrictDto districtDto = new DistrictDto();
        districtDto.setId(district.getId());
        districtDto.setName(district.getName());
        districtDto.setCityId(district.getCity().getId());
        return districtDto;
    }

    public CityDto toCityDto(City city){
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        cityDto.setDistricts(city.getDistricts().stream().map(District::getId).toList());
        return cityDto;
    }

    public City toCity(CityDto cityDto){
        var districts = cityDto.getDistricts().stream().map(districtService::getDistrictById).toList();
        return City.builder()
                .name(cityDto.getName())
                .isOld(false)
                .districts(districts)
                .build();
    }

    public CategoryDto toCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
   }

    public Category.CategoryBuilder toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName());
    }


    public District.DistrictBuilder toDistrict(DistrictDto districtDto){
        return District.builder()
                .id(districtDto.getId())
                .name(districtDto.getName())
                .isOld(false);
    }

    public RoleDto toRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }

    public Role.RoleBuilder toRole(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName());
    }
}
