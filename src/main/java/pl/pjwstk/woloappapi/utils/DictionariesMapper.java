package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.*;

@Mapper(componentModel = "spring")
public interface DictionariesMapper {

    default DistrictDto toDistrictDto(District district){
        DistrictDto districtDto = new DistrictDto();
        districtDto.setId(district.getId());
        districtDto.setName(district.getName());
        districtDto.setCity(district.getCity());
        return districtDto;
    }

    default CategoryDto toCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
   }

    default Category.CategoryBuilder toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName());
    }


    default District.DistrictBuilder toDistrict(DistrictDto districtDto){
        return District.builder()
                .id(districtDto.getId())
                .name(districtDto.getName())
                .city(districtDto.getCity());
    }

    default RoleDto toRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }

    default Role.RoleBuilder toRole(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName());
    }
}
