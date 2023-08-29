package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.repository.CategoryRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository CategoryRepository;

    @GetMapping("/all")
    Iterable<Category> all() {
        return CategoryRepository.findAll();
    }

    @GetMapping("/{id}")
    Category CategoryById(@PathVariable Long id) {
        return CategoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    Category save(@RequestBody Category Category) {
        return CategoryRepository.save(Category);
    }

}