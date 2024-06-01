package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.CityDto;
import pl.pjwstk.woloappapi.model.entities.City;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.repository.CityRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTests {
    @Mock
    private CityRepository cityRepository;

    @Mock
    private DictionariesMapper dictionariesMapper;

    @Mock
    private DistrictService districtService;

    @InjectMocks
    private CityService cityService;

    private City city;
    private CityDto cityDto;
    private City.CityBuilder cityBuilder;
    private District district1, district2;

    @BeforeEach
    public void setUp() {
        city = new City();
        city.setId(1L);
        city.setName("Test City");
        city.setOld(false);
        city.setDistricts(new ArrayList<>());

        cityDto = new CityDto();
        cityDto.setId(1L);
        cityDto.setName("Test City DTO");
        var list = new ArrayList<String>();
        list.add("District1");
        list.add("District2");
        cityDto.setDistricts(list);

        cityBuilder = City.builder()
                .id(cityDto.getId())
                .name(cityDto.getName());

        district1 = District.builder()
                .id(1L)
                .name("District1")
                .city(city)
                .build();

        district2 = District.builder()
                .id(2L)
                .name("District2")
                .city(city)
                .build();
    }

    @Test
    public void testGetAllCities() {
        when(cityRepository.findAll()).thenReturn(List.of(city));

        var result = cityService.getAllCities();

        assertEquals(1, result.size());
        assertEquals(city, result.get(0));
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    public void testGetActualAllCities() {
        when(cityRepository.getAllActualCities()).thenReturn(List.of(city));

        var result = cityService.getActualAllCities();

        assertEquals(1, result.size());
        assertEquals(city, result.get(0));
        verify(cityRepository, times(1)).getAllActualCities();
    }

    @Test
    public void testGetCityByIdCityExists() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        City result = cityService.getCityById(1L);

        assertEquals(city, result);
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCityByIdCityNotExists() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->cityService.getCityById(1L));

        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateCity() {
        when(dictionariesMapper.toCity(cityDto)).thenReturn(cityBuilder);

        cityService.createCity(cityDto);

        verify(cityRepository, times(1)).save(cityBuilder.build());
        verify(dictionariesMapper, times(1)).toCity(cityDto);
    }

    @Test
    public void testDeleteCityCityExists() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        cityService.deleteCity(1L);

        assertTrue(city.isOld());
        verify(cityRepository, times(1)).save(city);
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteCityCityNotExists() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cityService.deleteCity(1L));

        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateCityCityExists() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(districtService.getDistrictByName("District1")).thenReturn(district1);
        when(districtService.getDistrictByName("District2")).thenReturn(district2);

        cityService.updateCity(cityDto);

        assertEquals(cityDto.getName(), city.getName());
        verify(cityRepository, times(1)).save(city);
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateCityCityNotExists() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> cityService.updateCity(cityDto));

        verify(cityRepository, times(1)).findById(1L);
    }
}

