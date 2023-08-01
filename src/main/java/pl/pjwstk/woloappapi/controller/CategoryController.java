package pl.pjwstk.woloappapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.service.CategoryService;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/Category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getCategorys(){
        List<Category> Categorys = categoryService.getAllCategorys();
        return new ResponseEntity<>(Categorys, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addCategory(@RequestBody Category Category){
        categoryService.createCategory(Category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}