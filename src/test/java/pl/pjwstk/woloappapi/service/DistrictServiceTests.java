package pl.pjwstk.woloappapi.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.DistrictRequestDto;
import pl.pjwstk.woloappapi.model.entities.City;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.repository.CityRepository;
import pl.pjwstk.woloappapi.repository.DistrictRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DistrictServiceTests {

    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private DictionariesMapper dictionariesMapper;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private DistrictService districtService;

    private City city;
    private District district;
    private DistrictRequestDto districtRequestDto;
    private District.DistrictBuilder districtBuilder;

    @BeforeEach
    public void setUp() {
        city = new City();
        city.setId(1L);
        city.setName("Test City");
        city.setOld(false);
        city.setDistricts(new ArrayList<>());

        district = new District();
        district.setId(1L);
        district.setName("Test District");
        district.setCity(city);

        districtRequestDto = new DistrictRequestDto();
        districtRequestDto.setId(1L);
        districtRequestDto.setName("Test District DTO");
        districtRequestDto.setCityId(1L);

        districtBuilder = District.builder()
                .id(districtRequestDto.getId())
                .name(districtRequestDto.getName());
    }

    @Test
    public void testGetAllDistricts() {
        when(districtRepository.findAll()).thenReturn(List.of(district));

        var result = districtService.getAllDistricts();

        assertEquals(1, result.size());
        assertEquals(district, result.get(0));
        verify(districtRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllActualDistricts() {
        when(districtRepository.getAllActualDistricts()).thenReturn(List.of(district));

        var result = districtService.getAllActualDistricts();

        assertEquals(1, result.size());
        assertEquals(district, result.get(0));
        verify(districtRepository, times(1)).getAllActualDistricts();
    }

    @Test
    public void testGetDistrictByIdDistrictExists() {
        when(districtRepository.findById(1L)).thenReturn(Optional.of(district));

        var result = districtService.getDistrictById(1L);

        assertEquals(district, result);
        verify(districtRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetDistrictByIdDistrictNotExists() {
        when(districtRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> districtService.getDistrictById(1L));

        verify(districtRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetDistrictByNameDistrictExists() {
        when(districtRepository.findByName("Test District")).thenReturn(Optional.of(district));

        District result = districtService.getDistrictByName("Test District");

        assertEquals(district, result);
        verify(districtRepository, times(1)).findByName("Test District");
    }

    @Test
    public void testGetDistrictByNameDistrictNotExists() {
        when(districtRepository.findByName("Test District")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> districtService.getDistrictByName("Test District"));

        verify(districtRepository, times(1)).findByName("Test District");
    }

    @Test
    public void testCreateDistrict() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(dictionariesMapper.toDistrict(districtRequestDto)).thenReturn(districtBuilder);

        districtService.createDistrict(districtRequestDto);

        verify(cityRepository, times(1)).findById(1L);
        verify(districtRepository, times(1)).save(districtBuilder.city(city).build());
    }

    @Test
    public void testCreateDistrictCityNotExists() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> districtService.createDistrict(districtRequestDto));

        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteDistrictDistrictExists() {
        when(districtRepository.findById(1L)).thenReturn(Optional.of(district));

        districtService.deleteDistrict(1L);

        assertTrue(district.isOld());
        verify(districtRepository, times(1)).save(district);
    }

    @Test
    public void testDeleteDistrictDistrictNotExists() {
        when(districtRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> districtService.deleteDistrict(1L));

        verify(districtRepository, times(1)).findById(1L);
    }
    @Test
    public void testUpdateDistrictDistrictAndCityExist() {
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));
        when(districtRepository.findById(anyLong())).thenReturn(Optional.of(district));

        districtService.updateDistrict(districtRequestDto);

        verify(cityRepository, times(1)).findById(1L);
        verify(districtRepository, times(1)).findById(1L);
        verify(districtRepository, times(1)).save(district);
        assertEquals("Test District DTO", district.getName());
        assertEquals(city, district.getCity());
    }

    @Test
    public void testUpdateDistrictCityNotFound() {

        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> districtService.updateDistrict(districtRequestDto));
        assertEquals("City with ID " + 1L + " does not exist", exception.getMessage());
        verify(cityRepository, times(1)).findById(1L);
        verify(districtRepository, never()).findById(any());
        verify(districtRepository, never()).save(any());
    }

    @Test
    public void testUpdateDistrictDistrictNotFound() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(districtRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> districtService.updateDistrict(districtRequestDto));
        assertEquals("District with ID " + 1L + " does not exist", exception.getMessage());
        verify(cityRepository, times(1)).findById(1L);
        verify(districtRepository, times(1)).findById(1L);
        verify(districtRepository, never()).save(any());
    }
}
