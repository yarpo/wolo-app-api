package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.model.CategoryDto;
import pl.pjwstk.woloappapi.repository.CategoryRepository;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class CategoryService {
    private final Long basicCategory = 4L;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category id not found!"));
    }

    public void createCategory(CategoryDto categoryDto) {
        Category category = eventMapper.mapCategoryDtoToCategory(categoryDto);
        categoryRepository.save(category);
    }

    public Category updateCategory(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
             new IllegalArgumentException("Category with ID " + id + " does not exist"));

        updateFieldIfDifferent(category::getName, category::setName, categoryDto.getName());
        return categoryRepository.save(category);
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
    private <T> void updateFieldIfDifferent(
            Supplier<T> currentSupplier, Consumer<T> updateConsumer, T newValue) {
        if (!Objects.equals(currentSupplier.get(), newValue)) {
            updateConsumer.accept(newValue);

        }
    }
}
