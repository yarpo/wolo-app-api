package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.DistrictRequestDto;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.repository.CityRepository;
import pl.pjwstk.woloappapi.repository.DistrictRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final DictionariesMapper dictionariesMapper;
    private final CityRepository cityRepository;

    public List<District> getAllDistricts() {
        return districtRepository
            .findAll()
            .stream()
            .toList();
    }

    public District getDistrictById(Long id) {
        return districtRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("District id not found!"));
    }

    public District getDistrictByName(String name){
        return districtRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException("District name not found!"));
    }

    @Transactional
    public void createDistrict(DistrictRequestDto districtRequestDto) {
        var city = cityRepository.findById(districtRequestDto.getCityId())
                .orElseThrow(() ->
                        new IllegalArgumentException("City with ID " + districtRequestDto.getCityId() + " does not exist"));
        var district = dictionariesMapper.toDistrict(districtRequestDto)
                .city(city)
                .build();
        city.getDistricts().add(district);
        cityRepository.save(city);
    }


    @Transactional
    public void deleteDistrict(Long id) {
        var district = districtRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("District not found!"));
        district.setOld(true);
        districtRepository.save(district);
    }

    @Transactional
    public void updateDistrict(DistrictRequestDto districtRequestDto) {
        var city = cityRepository.findById(districtRequestDto.getCityId())
                .orElseThrow(() ->
                        new IllegalArgumentException("City with ID " + districtRequestDto.getCityId() + " does not exist"));
        var district = districtRepository
                .findById(districtRequestDto.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("District with ID " + districtRequestDto.getCityId() + " does not exist"));
        district.setName(districtRequestDto.getName());
        district.setCity(city);
        districtRepository.save(district);
    }
}
