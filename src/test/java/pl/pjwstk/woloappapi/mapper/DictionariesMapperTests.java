package pl.pjwstk.woloappapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.admin.CityResponseAdminDto;
import pl.pjwstk.woloappapi.model.admin.DistrictResponseAdminDto;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DictionariesMapperTests {
    private final DictionariesMapper dictionariesMapper = new DictionariesMapper();

    @Test
    public void testToDistrictDto() {
        District district = new District();
        district.setId(1L);
        district.setName("Sample District");

        City city = new City();
        city.setId(1L);
        city.setName("Sample City");
        district.setCity(city);

        DistrictResponseDto districtDto = dictionariesMapper.toDistrictDto(district);

        assertEquals(1L, districtDto.getId());
        assertEquals("Sample District", districtDto.getName());
        assertEquals("Sample City", districtDto.getCityName());
    }

    @Test
    public void testToDistrictResponseAdminDto() {
        var city = City.builder()
                .id(1L)
                .name("Sample City")
                .build();
        var district = District.builder()
                .id(1L)
                .name("Sample District")
                .isOld(false)
                .city(city)
                .build();

        DistrictResponseAdminDto districtDto = dictionariesMapper.toDistrictResponseAdminDto(district);

        assertEquals(1L, districtDto.getId());
        assertEquals("Sample District", districtDto.getName());
        assertEquals("Sample City", districtDto.getCityName());
        assertFalse(districtDto.isOld());
    }

    @Test
    public void testToCityDto() {
        var city = City.builder()
                        .id(1L)
                        .name("Sample City")
                        .build();

        var district1 = District.builder()
                                        .name("District 1")
                                        .build();
        var district2 = District.builder()
                .name("District 2")
                .build();

        city.setDistricts(Arrays.asList(district1, district2));

        CityDto cityDto = dictionariesMapper.toCityDto(city);

        assertEquals(1L, cityDto.getId());
        assertEquals("Sample City", cityDto.getName());
        assertEquals(Arrays.asList("District 1", "District 2"), cityDto.getDistricts());
    }
    @Test
    public void testToCityResponseAdminDto() {
        City city = new City();
        city.setId(1L);
        city.setName("Sample City");
        city.setOld(false);

        District district1 = new District();
        district1.setName("District 1");
        District district2 = new District();
        district2.setName("District 2");

        city.setDistricts(Arrays.asList(district1, district2));

        CityResponseAdminDto cityResponseAdminDto = dictionariesMapper.toCityResponseAdminDto(city);

        assertEquals(1L, cityResponseAdminDto.getId());
        assertEquals("Sample City", cityResponseAdminDto.getName());
        assertEquals(Arrays.asList("District 1", "District 2"), cityResponseAdminDto.getDistricts());
        assertFalse(cityResponseAdminDto.isOld());
    }

    @Test
    public void testToCityWithId() {
        var cityDto = CityDto.builder()
                .id(1L)
                .name("Sample City")
                .build();

        var city = dictionariesMapper.toCity(cityDto).build();

        assertEquals(1L, city.getId());
        assertEquals("Sample City", city.getName());
        assertFalse(city.isOld());
    }

    @Test
    public void testToCityWithNoId() {
        var cityDto = CityDto.builder()
                .name("Sample City")
                .districts(Arrays.asList("District1", "District2"))
                .build();

        var city = dictionariesMapper.toCity(cityDto).build();

        assertNotNull(city);
        assertEquals("Sample City", city.getName());
        assertFalse(city.isOld());
    }

    @Test
    public void testToCategoryDto() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Sample Category");

        CategoryDto categoryDto = dictionariesMapper.toCategoryDto(category);

        assertEquals(1L, categoryDto.getId());
        assertEquals("Sample Category", categoryDto.getName());
    }
    @Test
    public void testToCategory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Sample Category");

        Category category = dictionariesMapper.toCategory(categoryDto).build();

        assertEquals(1L, category.getId());
        assertEquals("Sample Category", category.getName());
    }

    @Test
    public void testToDistrict(){
        DistrictRequestDto districtDto = new DistrictRequestDto();
        districtDto.setId(1L);
        districtDto.setName("Sample District");

        District district = dictionariesMapper.toDistrict(districtDto).build();

        assertEquals(1L, district.getId());
        assertEquals("Sample District", district.getName());
        assertFalse(district.isOld());
    }


    @Test public void testToRoleDto(){
        Role role = new Role();
        role.setId(1L);
        role.setName("Sample Role");

        RoleDto roleDto = dictionariesMapper.toRoleDto(role);

        assertEquals(1L, roleDto.getId());
        assertEquals("Sample Role", roleDto.getName());
    }

    @Test
    public void testToRole(){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setName("Sample Role");

        Role role = dictionariesMapper.toRole(roleDto).build();

        assertEquals(1L, role.getId());
        assertEquals("Sample Role", role.getName());
    }

    @Test
    public void testToFAQDto() {
        var faq = FAQ.builder()
                .id(1L)
                .questionPL("Pytanie PL")
                .answerPL("Odpowiedź PL")
                .questionEN("Question EN")
                .answerEN("Answer EN")
                .questionUA("Питання UA")
                .answerUA("Відповідь UA")
                .questionRU("Вопрос RU")
                .answerRU("Ответ RU")
                .build();

        var faqResponseDto = dictionariesMapper.toFAQDto(faq);

        assertNotNull(faqResponseDto);
        assertEquals(1L, faqResponseDto.getId());
        assertEquals("Pytanie PL", faqResponseDto.getQuestionPL());
        assertEquals("Odpowiedź PL", faqResponseDto.getAnswerPL());
        assertEquals("Question EN", faqResponseDto.getQuestionEN());
        assertEquals("Answer EN", faqResponseDto.getAnswerEN());
        assertEquals("Питання UA", faqResponseDto.getQuestionUA());
        assertEquals("Відповідь UA", faqResponseDto.getAnswerUA());
        assertEquals("Вопрос RU", faqResponseDto.getQuestionRU());
        assertEquals("Ответ RU", faqResponseDto.getAnswerRU());
    }

    @Test
    public void testToFAQ() {
        var faqRequestDto = FAQEditRequestDto.builder()
                .id(1L)
                .questionPL("Pytanie PL")
                .answerPL("Odpowiedź PL")
                .questionEN("Question EN")
                .answerEN("Answer EN")
                .questionUA("Питання UA")
                .answerUA("Відповідь UA")
                .questionRU("Вопрос RU")
                .answerRU("Ответ RU")
                .build();

        var faq = dictionariesMapper.toFAQ(faqRequestDto).build();

        assertNotNull(faq);
        assertEquals(1L, faq.getId());
        assertEquals("Pytanie PL", faq.getQuestionPL());
        assertEquals("Odpowiedź PL", faq.getAnswerPL());
        assertEquals("Question EN", faq.getQuestionEN());
        assertEquals("Answer EN", faq.getAnswerEN());
        assertEquals("Питання UA", faq.getQuestionUA());
        assertEquals("Відповідь UA", faq.getAnswerUA());
        assertEquals("Вопрос RU", faq.getQuestionRU());
        assertEquals("Ответ RU", faq.getAnswerRU());
    }
}
