package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.District;
import pl.pjwstk.woloappapi.repository.DistrictRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    public District getDistrictById(Long id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("District id not found!"));
    }

    public void createDistrict(District district) {
        districtRepository.save(district);
    }

    public void deleteCDistrict(Long id) {
        if (!districtRepository.existsById(id)) {
            throw new IllegalArgumentException("District with ID " + id + " does not exist");
        }
        districtRepository.deleteById(id);
    }

    public void updateDistrict(District district, Long id) {
        if (!districtRepository.existsById(id)) {
            throw new IllegalArgumentException("District with ID " + id + " does not exist");
        }
        district.setId(id);
        districtRepository.save(district);
    }
}
