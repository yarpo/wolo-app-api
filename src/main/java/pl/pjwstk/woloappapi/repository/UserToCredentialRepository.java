package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.Credential;
import pl.pjwstk.woloappapi.model.UserToCredential;





@Repository
public interface UserToCredentialRepository extends JpaRepository<UserToCredential,Long> {
}
