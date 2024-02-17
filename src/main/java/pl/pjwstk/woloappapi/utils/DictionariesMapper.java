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

    default Category toCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(category.getName());
        return category;
    }

    default District toDistrict(DistrictDto districtDto){
        District district = new District();
        district.setId(districtDto.getId());
        district.setName(districtDto.getName());
        district.setCity(districtDto.getCity());
        return district;
    }

    default RoleDto toRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }

    default Role toRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }
}
