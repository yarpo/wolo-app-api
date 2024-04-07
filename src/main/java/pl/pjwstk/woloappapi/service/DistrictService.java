package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.DistrictDto;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.repository.CityRepository;
import pl.pjwstk.woloappapi.repository.DistrictRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;
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
            .filter(d -> d.isOld() == false)
            .toList();
    }

    public District getDistrictById(Long id) {
        return districtRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("District id not found!"));
    }

    @Transactional
    public void createDistrict(DistrictDto districtDto) {
        var city = cityRepository.findById(districtDto.getCityId())
                .orElseThrow(() ->
                        new IllegalArgumentException("City with ID " + districtDto.getCityId() + " does not exist"));
        var district = dictionariesMapper.toDistrict(districtDto)
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
    public void updateDistrict(DistrictDto districtDto) {
        var city = cityRepository.findById(districtDto.getCityId())
                .orElseThrow(() ->
                        new IllegalArgumentException("City with ID " + districtDto.getCityId() + " does not exist"));
        var district = districtRepository
                .findById(districtDto.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("District with ID " + districtDto.getId() + " does not exist"));
        district.setName(districtDto.getName());
        district.setCity(city);
        districtRepository.save(district);
    }
}
