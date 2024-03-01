package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjwstk.woloappapi.model.Category;
import pl.pjwstk.woloappapi.model.CategoryDto;
import pl.pjwstk.woloappapi.service.CategoryService;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
@Tag(name = "Categories")

public class CategoryController {

    private final CategoryService categoryService;
    private final DictionariesMapper dictionariesMapper;

    @Operation(
            summary = "Get all categories",
            responses = {
                    @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(type = "array",implementation = CategoryDto.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDto> categoryDtos =
                categories.stream().map(dictionariesMapper::toCategoryDto).toList();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Get category by id",
            description = "Category must exist",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200" ,
                                content = {
                                        @Content(
                                                mediaType = "application/json",
                                                schema = @Schema(implementation = CategoryDto.class)
                                        )
                                }

                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "Category id",
                            example = "1"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = dictionariesMapper
                .toCategoryDto(categoryService.getCategoryById(id));
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Adding new category",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            },
            parameters = {
                    @Parameter(name = "category",
                            description = "Category object to create",
                            schema = @Schema(implementation = CategoryDto.class)
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addCategory(@Valid @RequestBody CategoryDto category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete category",
            description = "All events from this category will be assigned to Basic category",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "Category id",
                            example = "1"
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Edit category",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            },
            parameters = {
                    @Parameter(name = "category",
                            description = "Category object with changes",
                            schema = @Schema(implementation = CategoryDto.class)
                    )
            }
    )
    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editCategory(
            @Valid @RequestBody CategoryDto category) {
        categoryService.updateCategory(category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
