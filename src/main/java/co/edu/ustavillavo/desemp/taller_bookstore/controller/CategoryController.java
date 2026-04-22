package co.edu.ustavillavo.desemp.taller_bookstore.controller;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.CategoryRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.ApiResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.CategoryResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(categoryService.create(request), "Category created", 201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.findById(id), "Category found"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.findAll(), "Categories retrieved"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(@PathVariable Long id,
                                                                @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.update(id, request), "Category updated"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Category deleted"));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> findBooks(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.findBooksByCategory(id, pageable), "Books by category"));
    }
}