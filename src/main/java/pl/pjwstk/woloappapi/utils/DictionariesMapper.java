package pl.pjwstk.woloappapi.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.admin.CityResponseAdminDto;
import pl.pjwstk.woloappapi.model.admin.DistrictResponseAdminDto;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.model.translation.FAQTranslationResponse;

@Component
@RequiredArgsConstructor
public class DictionariesMapper {
    public DistrictResponseDto toDistrictDto(District district){
        return DistrictResponseDto.builder()
                .id(district.getId())
                .name(district.getName())
                .cityName(district.getCity().getName())
                .build();
    }

    public DistrictResponseAdminDto toDistrictResponseAdminDto(District district){
        return DistrictResponseAdminDto.builder()
                .id(district.getId())
                .name(district.getName())
                .cityName(district.getCity().getName())
                .isOld(district.isOld())
                .build();
    }

    public CityDto toCityDto(City city){
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        cityDto.setDistricts(city.getDistricts().stream().map(District::getName).toList());
        return cityDto;
    }

    public CityResponseAdminDto toCityResponseAdminDto(City city){
        CityResponseAdminDto cityResponseAdminDto = new CityResponseAdminDto();
        cityResponseAdminDto.setId(city.getId());
        cityResponseAdminDto.setName(city.getName());
        cityResponseAdminDto.setDistricts(city.getDistricts().stream().map(District::getName).toList());
        cityResponseAdminDto.setOld(city.isOld());
        return cityResponseAdminDto;
    }

    public City.CityBuilder toCity(CityDto cityDto){
        return City.builder()
                .id(cityDto.getId())
                .name(cityDto.getName())
                .isOld(false);
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


    public District.DistrictBuilder toDistrict(DistrictRequestDto districtRequestDto){
        return District.builder()
                .id(districtRequestDto.getId())
                .name(districtRequestDto.getName())
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

    public FAQResponseDto toFAQDto(FAQ faq){
        FAQResponseDto faqResponseDto = new FAQResponseDto();
        faqResponseDto.setId(faq.getId());
        faqResponseDto.setQuestionPL(faq.getQuestionPL());
        faqResponseDto.setAnswerPL(faq.getAnswerPL());
        faqResponseDto.setQuestionEN(faq.getQuestionEN());
        faqResponseDto.setAnswerEN(faq.getAnswerEN());
        faqResponseDto.setQuestionUA(faq.getQuestionUA());
        faqResponseDto.setAnswerUA(faq.getAnswerUA());
        faqResponseDto.setQuestionRU(faq.getQuestionRU());
        faqResponseDto.setAnswerRU(faq.getAnswerRU());
        return faqResponseDto;
    }

    public FAQ.FAQBuilder toFAQ(FAQDto faqDto, FAQTranslationResponse translation){
        return FAQ.builder()
                .id(faqDto.getId())
                .questionPL(translation.getQuestionPL())
                .answerPL(translation.getAnswerPL())
                .questionEN(translation.getQuestionEN())
                .answerEN(translation.getAnswerEN())
                .questionUA(translation.getQuestionUA())
                .answerUA(translation.getAnswerUA())
                .questionRU(translation.getQuestionRU())
                .answerRU(translation.getAnswerRU());
    }
}
