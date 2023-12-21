package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.Credential;

@Repository
public interface CredentialRepository extends JpaRepository<Credential,Long> {
}
