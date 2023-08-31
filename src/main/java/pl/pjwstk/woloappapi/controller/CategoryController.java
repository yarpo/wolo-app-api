package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.service.CategoryService;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    Iterable<Category> all() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    Category CategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping("/save")
    Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }

}
