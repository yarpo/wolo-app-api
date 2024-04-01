package pl.pjwstk.woloappapi.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.DistrictDto;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.repository.DistrictRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DistrictServiceTests {

    @Mock
    private DictionariesMapper dictionariesMapper;

    @Mock
    private DistrictRepository districtRepository;

    @InjectMocks
    private DistrictService districtService;

    @Test
    public void testGetAllDisctricts(){
        District district1 = new District();
        District district2 = new District();

        when(districtRepository.findAll()).thenReturn(Arrays.asList(district1, district2));

        List<District> actualDistricts = districtService.getAllDistricts();

        assertEquals(2, actualDistricts.size());
    }


    @Test
    public void testGetByDistrictId(){
        District district = new District();
        district.setId(1L);
        when(districtRepository.findById(1L)).thenReturn(Optional.of(district));

        District retrievedDistrict = districtService.getDistrictById(1L);

        assertEquals(district.getId(), retrievedDistrict.getId());
    }

    @Test
    public void testCreateDistrict(){
        DistrictDto districtDto = new DistrictDto();
        districtDto.setId(1L);
        districtDto.setName("Test Name");
        districtDto.setCity("Test City");

        when(dictionariesMapper.toDistrict(districtDto)).thenReturn(District.builder()
                .id(districtDto.getId())
                .name(districtDto.getName())
                .city(districtDto.getCity()));

        districtService.createDistrict(districtDto);

        ArgumentCaptor<District> districtCaptor = ArgumentCaptor.forClass(District.class);
        verify(districtRepository).save(districtCaptor.capture());
        District capturedDistrict = districtCaptor.getValue();
        assertEquals(1L, capturedDistrict.getId());
        assertEquals("Test Name", capturedDistrict.getName());
        assertEquals("Test City", capturedDistrict.getCity());
    }

    @Test
    public void testDeleteDistrict() {
        District district = new District();
        district.setId(1L);

        when(districtRepository.findById(1L)).thenReturn(Optional.of(district));

        districtService.deleteDistrict(1L);

        ArgumentCaptor<District> districtCaptor = ArgumentCaptor.forClass(District.class);
        verify(districtRepository).save(districtCaptor.capture());
        District capturedDistrict = districtCaptor.getValue();
        assertEquals(1L, capturedDistrict.getId());
        assertTrue(capturedDistrict.isOld());
    }
}
