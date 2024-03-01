package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.model.CategoryDto;
import pl.pjwstk.woloappapi.service.CategoryService;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final DictionariesMapper dictionariesMapper;

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDto> categoryDtos =
                categories.stream().map(dictionariesMapper::toCategoryDto).toList();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = dictionariesMapper
                .toCategoryDto(categoryService.getCategoryById(id));
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addCategory(@Valid @RequestBody CategoryDto category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editCategory(
            @Valid @RequestBody CategoryDto category) {
        categoryService.updateCategory(category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
