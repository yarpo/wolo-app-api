package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pjwstk.woloappapi.model.CityDto;
import pl.pjwstk.woloappapi.model.entities.City;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.repository.CityRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CityServiceTests {
    @Mock
    private CityRepository cityRepository;
    @Mock
    private DistrictService districtService;

    @Mock
    private DictionariesMapper dictionariesMapper;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCities() {
        List<City> cities = List.of(
                new City(1L, "City1",false, new ArrayList<>(){}, new ArrayList<>()),
                new City(2L, "City2",false, new ArrayList<>(){}, new ArrayList<>()));
        when(cityRepository.findAll()).thenReturn(cities);

        var result = cityService.getAllCities();

        assertEquals(2, result.size());
        assertEquals("City1", result.get(0).getName());
        assertEquals("City2", result.get(1).getName());
    }

    @Test
    public void testGetCityById() {
        Long cityId = 1L;
        City city = new City(cityId, "City1", false, new ArrayList<>(){}, new ArrayList<>());
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

        City result = cityService.getCityById(cityId);

        assertEquals(cityId, result.getId());
        assertEquals("City1", result.getName());
    }

    @Test
    public void testCreateCity() {
        var cityDto = new CityDto(null, "New City", List.of(1L, 2L));

        when(dictionariesMapper.toCity(cityDto)).thenReturn(
                City.builder()
                        .name(cityDto.getName())
                        .isOld(false)
        );
        when(districtService.getDistrictById(1L))
                .thenReturn(new District(1L, "District1", false, null, null));
        when(districtService.getDistrictById(2L))
                .thenReturn(new District(2L, "District2", false, null, null));

        cityService.createCity(cityDto);

        verify(cityRepository, times(1)).save(any(City.class));
    }

    @Test
    public void testUpdateCity() {
        var cityId = 1L;
        var cityDto = new CityDto(cityId, "Updated City", List.of(1L, 2L));

        City existingCity = new City(cityId, "City1", false, new ArrayList<>(), null);
        existingCity.getDistricts().add(new District(1L, "District1", false, existingCity, null));
        existingCity.getDistricts().add(new District(2L, "District2", false, existingCity, null));

        when(cityRepository.findById(cityId)).thenReturn(Optional.of(existingCity));
        var updatedCity = City.builder()
                .id(cityId)
                .name(cityDto.getName())
                .isOld(false);

        when(dictionariesMapper.toCity(cityDto)).thenReturn(updatedCity);

        cityService.updateCity(cityDto);

        assertEquals("Updated City", existingCity.getName());
        assertEquals(2, existingCity.getDistricts().size());
        verify(cityRepository, times(1)).save(existingCity);
    }



    @Test
    public void testDeleteCity() {
        Long cityId = 1L;
        City city = new City(cityId, "City1", false, null, null);
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

        cityService.deleteCity(cityId);

        assertTrue(city.isOld());
        verify(cityRepository, times(1)).save(city);
    }
}

