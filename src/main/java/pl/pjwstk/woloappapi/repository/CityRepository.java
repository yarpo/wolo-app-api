package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.entities.Category;
import pl.pjwstk.woloappapi.model.entities.City;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);

    @Query("SELECT e FROM City e " +
            "WHERE e.isOld = false")
    List<City> getAllActualCities();

}
