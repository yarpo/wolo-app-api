package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pjwstk.woloappapi.database.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
