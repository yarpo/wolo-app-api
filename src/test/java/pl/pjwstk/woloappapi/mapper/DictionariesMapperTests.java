package pl.pjwstk.woloappapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.*;
import pl.pjwstk.woloappapi.model.entities.Category;
import pl.pjwstk.woloappapi.model.entities.City;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.model.entities.Role;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DictionariesMapperTests {

    DictionariesMapper dictionariesMapper = new DictionariesMapper();

    @Test
    public void testToDistrictDto() {
        District district = new District();
        district.setId(1L);
        district.setName("Sample District");

        City city = new City();
        city.setId(1L);
        city.setName("Sample City");
        district.setCity(city);

        DistrictDto districtDto = dictionariesMapper.toDistrictDto(district);

        assertEquals(1L, districtDto.getId());
        assertEquals("Sample District", districtDto.getName());
        assertEquals(1L, districtDto.getCityId());
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
    public void testToDisctrict(){
        DistrictDto districtDto = new DistrictDto();
        districtDto.setId(1L);
        districtDto.setName("Sample District");

        District district = dictionariesMapper.toDistrict(districtDto).build();

        assertEquals(1L, district.getId());
        assertEquals("Sample District", district.getName());
    }

    @Test
    public void testToRoleDto(){
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
}
