package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.model.CategoryDto;
import pl.pjwstk.woloappapi.model.District;
import pl.pjwstk.woloappapi.model.DistrictDto;

@Mapper(componentModel = "spring")
public interface DictionariesMapper {
    @Mapping(target = "id", source = "district.id")
    @Mapping(target = "name", source = "district.name")
    @Mapping(target = "city", source = "district.city")
    DistrictDto toDistrictDto(District district);

   @Mapping(target = "id", source = "category.id")
   @Mapping(target = "name", source = "category.name")
   CategoryDto toCategoryDto(Category category);
}
