package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjwstk.woloappapi.model.entities.FAQ;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
