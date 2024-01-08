package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.repository.CategoryRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final Long basicCategory = 4L;
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category id not found!"));
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category category, Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category with ID " + id + " does not exist");
        }
        category.setId(id);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository
                .findById(id)
                .ifPresent(
                        category -> {
                            category.getCategoryToEventList()
                                    .forEach(
                                            cte ->
                                                    cte.setCategory(
                                                            categoryRepository
                                                                    .findById(basicCategory)
                                                                    .orElse(null)));
                            categoryRepository.deleteById(id);
                        });
    }
}
