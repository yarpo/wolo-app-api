package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.CategoryDto;
import pl.pjwstk.woloappapi.model.entities.Category;
import pl.pjwstk.woloappapi.model.entities.CategoryToEvent;
import pl.pjwstk.woloappapi.repository.CategoryRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @Mock
    private DictionariesMapper dictionariesMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testGetAllCategories(){
        Category category1 = new Category();
        Category category2 = new Category();

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> actualCategories = categoryService.getAllCategories();

        assertEquals(2, actualCategories.size());
    }

    @Test
    public void testGetCategoryById_CategoryNotFound() {
        var categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class,
                () ->  categoryService.getCategoryById(categoryId));
        assertEquals("Category id not found!", exception.getMessage());
    }

    @Test
    public void testUpdateCategory_CategoryNotFound() {
        var categoryDto = new CategoryDto(1L, "Updated Category");
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () ->categoryService.updateCategory(categoryDto));
        assertEquals("Category with ID " + categoryDto.getId() + " does not exist", exception.getMessage());
    }

    @Test
    public void testGetCategoryById(){
        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category retrievedCategory = categoryService.getCategoryById(1L);

        assertEquals(category.getId(), retrievedCategory.getId());
    }

    @Test
    public void testCreateCategory(){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Test Category");

        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        when(dictionariesMapper.toCategory(categoryDto)).thenReturn(Category.builder()
                .id(category.getId())
                .name(category.getName()));

        categoryService.createCategory(categoryDto);

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryCaptor.capture());
        Category capturedCategory = categoryCaptor.getValue();
        assertEquals(1L, capturedCategory.getId());
        assertEquals("Test Category", capturedCategory.getName());
    }

    @Test
    public void testUpdateCategory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("New Category Name");

        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName("Old Category Name");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.updateCategory(categoryDto);

        verify(categoryRepository, times(1)).save(category);
        assertEquals("New Category Name", category.getName());
    }

    @Test
    public void testDeleteCategory() {
        var category = Category.builder().id(1L).name("sample").build();
        var basicCategory = Category.builder().id(5L).name("Podstawowa").build();
        var categoryToEvent1 = new CategoryToEvent();
        categoryToEvent1.setCategory(category);
        var categoryToEvent2 = new CategoryToEvent();
        categoryToEvent2.setCategory(category);
        category.setCategoryToEventList(Arrays.asList(categoryToEvent1, categoryToEvent2));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.findByName("Podstawowa")).thenReturn(Optional.of(basicCategory));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
        assertEquals("Podstawowa", categoryToEvent1.getCategory().getName());
        assertEquals("Podstawowa", categoryToEvent2.getCategory().getName());
    }

}
