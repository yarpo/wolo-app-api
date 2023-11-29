package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.AddressToEvent;
@Repository
public interface AddressToEventRepository extends JpaRepository<AddressToEvent, Long> {

}
