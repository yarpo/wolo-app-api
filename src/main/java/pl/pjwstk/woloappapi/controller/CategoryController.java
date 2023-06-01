package pl.pjwstk.woloappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.repository.CategoryRepository;

@RestController
public class CategoryController {

    private CategoryRepository CategoryRepository;

    @Autowired
    public CategoryController(CategoryRepository CategoryRepository) {
        this.CategoryRepository = CategoryRepository;
    }

    @GetMapping("/Category/all")
    Iterable<Category> all() {
        return CategoryRepository.findAll();
    }

    @GetMapping("/Category/{id}")
    Category CategoryById(@PathVariable Long id) {
        return CategoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Category/save")
    Category save(@RequestBody Category Category) {
        return CategoryRepository.save(Category);
    }

}