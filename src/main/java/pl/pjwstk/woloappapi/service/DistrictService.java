package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.District;
import pl.pjwstk.woloappapi.model.DistrictDto;
import pl.pjwstk.woloappapi.repository.DistrictRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final DictionariesMapper dictionariesMapper;
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
        districtRepository.save(dictionariesMapper.toDistrict(districtDto).build());
    }


    public void deleteDistrict(Long id) {
        var district = districtRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("District not found!"));
        district.setOld(true);
        districtRepository.save(district);
    }

    @Transactional
    public void updateDistrict(DistrictDto districtDto) {
        District district = districtRepository
                .findById(districtDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("District with ID " + districtDto.getId() + " does not exist"));
        district.setName(districtDto.getName());
        district.setCity(districtDto.getCity());
        districtRepository.save(district);
    }
}
