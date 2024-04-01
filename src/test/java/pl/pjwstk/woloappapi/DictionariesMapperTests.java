package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.entities.Category;
import pl.pjwstk.woloappapi.model.CategoryDto;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.model.DistrictDto;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class DictionariesMapperTests {
    private DictionariesMapper createDictionariesMapper() {
        return new DictionariesMapper();
    }

    @Test
    public void testToDistrictDto() {
        DictionariesMapper dictionariesMapper = createDictionariesMapper();
        District district = new District();
        district.setId(1L);
        district.setName("Sample District");
        district.setCity("Sample City");

        DistrictDto districtDto = dictionariesMapper.toDistrictDto(district);

        assertNotNull(districtDto);
        assertEquals(1L, districtDto.getId());
        assertEquals("Sample District", districtDto.getName());
        assertEquals("Sample City", districtDto.getCity());
    }

    @Test
    public void testToCategoryDto() {
        DictionariesMapper dictionariesMapper = createDictionariesMapper();
        Category category = new Category();
        category.setId(1L);
        category.setName("Sample Category");

        CategoryDto categoryDto = dictionariesMapper.toCategoryDto(category);

        assertNotNull(categoryDto);
        assertEquals(1L, categoryDto.getId());
        assertEquals("Sample Category", categoryDto.getName());
    }
}
