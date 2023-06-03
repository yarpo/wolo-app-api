package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjwstk.woloappapi.model.AddressToEvent;

public interface AddressToEventRepository extends JpaRepository<AddressToEvent, Long> {
}
