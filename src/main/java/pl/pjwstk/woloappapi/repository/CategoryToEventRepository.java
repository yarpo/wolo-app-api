package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.CategoryToEvent;

@Repository
public interface CategoryToEventRepository extends JpaRepository<CategoryToEvent, Long> {}
