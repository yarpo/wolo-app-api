package pl.pjwstk.woloappapi.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pjwstk.woloappapi.model.Person;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PersonRepository extends CrudRepository<Person, Long> {

}
