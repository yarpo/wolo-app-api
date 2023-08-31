package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.repository.CategoryRepository;
import pl.pjwstk.woloappapi.utils.EventNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new EventNotFoundException("Category id not found!"));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
