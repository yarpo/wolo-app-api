package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.pjwstk.woloappapi.model.entities.City;
import pl.pjwstk.woloappapi.model.entities.District;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    Optional<District> findByName(String name);

    @Query("SELECT e FROM District e " +
            "WHERE e.isOld = false")
    List<District> getAllActualDistricts();
}
