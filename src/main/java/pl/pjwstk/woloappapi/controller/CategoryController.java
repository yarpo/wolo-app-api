package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.repository.CategoryRepository;

@RestController
@AllArgsConstructor
public class CategoryController {

    private CategoryRepository CategoryRepository;

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