package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjwstk.woloappapi.model.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
}
