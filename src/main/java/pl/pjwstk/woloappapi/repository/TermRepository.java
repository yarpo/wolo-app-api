package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.pjwstk.woloappapi.model.Term;

@RepositoryRestResource
public interface TermRepository extends JpaRepository<Term, Long> {

}