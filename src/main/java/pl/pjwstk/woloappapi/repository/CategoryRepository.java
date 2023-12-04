package pl.pjwstk.woloappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pjwstk.woloappapi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
