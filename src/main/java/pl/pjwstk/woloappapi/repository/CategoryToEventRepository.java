package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjwstk.woloappapi.model.CategoryToEvent;
public interface CategoryToEventRepository extends JpaRepository<CategoryToEvent, Long> {
}
