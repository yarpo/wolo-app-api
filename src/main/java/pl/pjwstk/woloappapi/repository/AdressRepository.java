package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.pjwstk.woloappapi.model.Address;
import pl.pjwstk.woloappapi.model.Category;

@RepositoryRestResource
public interface AdressRepository extends JpaRepository<Address, Long> {
}
