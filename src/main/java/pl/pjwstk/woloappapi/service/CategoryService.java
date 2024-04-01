package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.entities.Category;
import pl.pjwstk.woloappapi.model.CategoryDto;
import pl.pjwstk.woloappapi.repository.CategoryRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final DictionariesMapper dictionariesMapper;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category id not found!"));
    }

    @Transactional
    public void createCategory(CategoryDto category) {
        categoryRepository.save(dictionariesMapper.toCategory(category).build());
    }

    @Transactional
    public void updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository
                .findById(categoryDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + categoryDto.getId() + " does not exist"));
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository
                .findById(id)
                .ifPresent(c -> {
                        c.getCategoryToEventList()
                                .forEach(cte -> cte.setCategory(categoryRepository
                                                                            .findByName("Podstawowa")
                                                                            .get()));
                        categoryRepository.deleteById(id);
                });
    }
}
