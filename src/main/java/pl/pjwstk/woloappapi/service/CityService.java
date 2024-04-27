package pl.pjwstk.woloappapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.CityDto;
import pl.pjwstk.woloappapi.model.entities.City;
import pl.pjwstk.woloappapi.repository.CityRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final DictionariesMapper dictionariesMapper;
    private final DistrictService districtService;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(()-> new NotFoundException("City id not found"));
    }

    @Transactional
    public void createCity(CityDto city) {
        cityRepository.save(dictionariesMapper.toCity(city).build());
    }

    @Transactional
    public void deleteCity(Long id) {
        var city = cityRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("City not found!"));
        city.setOld(true);
        cityRepository.save(city);
    }

    @Transactional
    public void updateCity(CityDto cityDto) {
        var city = cityRepository.findById(cityDto.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("City with ID " + cityDto.getId() + " does not exist"));
        updateCityDistricts(city, cityDto);
        city.setName(cityDto.getName());
        cityRepository.save(city);
    }

    private void updateCityDistricts(City city, CityDto cityDto) {
        city.getDistricts().removeIf(d ->!cityDto.getDistricts()
                .contains(d.getName()));

        cityDto.getDistricts().stream()
                .filter(districtDto -> city.getDistricts().stream()
                        .noneMatch(existingDistrict -> existingDistrict.getName().equals(districtDto)))
                .map(districtService::getDistrictByName)
                .forEach(city.getDistricts()::add);
    }
}
