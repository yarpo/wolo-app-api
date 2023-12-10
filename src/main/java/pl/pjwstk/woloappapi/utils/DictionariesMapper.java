package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.model.CategoryDto;
import pl.pjwstk.woloappapi.model.District;
import pl.pjwstk.woloappapi.model.DistrictDto;

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
}
