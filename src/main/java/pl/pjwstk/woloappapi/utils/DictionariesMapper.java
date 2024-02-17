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
}
