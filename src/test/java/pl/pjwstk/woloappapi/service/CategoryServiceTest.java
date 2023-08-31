package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.repository.CategoryRepository;
import pl.pjwstk.woloappapi.utils.EventNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private final static Long ID = 1L;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAllShouldReturnList() {
        when(categoryRepository.findAll()).thenReturn(List.of(buildEntity(ID), buildEntity(2L)));

        List<Category> allCategories = categoryService.getAll();

        verify(categoryRepository, times(1)).findAll();
        assertThat(allCategories.size(), is(2));
    }

    @Test
    void getByIdReturnsEntity() {
        when(categoryRepository.findById(ID)).thenReturn(Optional.of(
                buildEntity(ID)
        ));

        Category category = categoryService.getById(ID);

        verify(categoryRepository, times(1)).findById(eq(ID));
        assertThat(category.getId(), is(ID));
        assertThat(category.getName(), is("category1"));
    }

    @Test
    void getByIdThrowsEventNotFoundException() {
        when(categoryRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> categoryService.getById(ID));
    }

    @Test
    void saveShouldCallRepository() {
        Category categoryToSave = buildEntity(ID);
        Category savedCategory = buildEntity(ID);
        when(categoryRepository.save(categoryToSave)).thenReturn(savedCategory);

        assertEquals(categoryToSave, categoryService.save(categoryToSave));

        verify(categoryRepository, times(1)).save(categoryToSave);
    }

    private static Category buildEntity(long id) {
        return new Category(id, "category" + id, Collections.emptyList());
    }
}
