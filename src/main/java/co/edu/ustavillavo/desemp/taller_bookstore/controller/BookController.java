package co.edu.ustavillavo.desemp.taller_bookstore.controller;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.BookRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.ApiResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponse>> create(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(bookService.create(request), "Book created", 201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(bookService.findById(id), "Book found"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookResponse>>> findAll(
            Pageable pageable,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long categoryId) {

        Page<BookResponse> result;
        if (authorId != null) {
            result = bookService.findByAuthor(authorId, pageable);
        } else if (categoryId != null) {
            result = bookService.findByCategory(categoryId, pageable);
        } else {
            result = bookService.findAll(pageable);
        }
        return ResponseEntity.ok(ApiResponse.success(result, "Books retrieved"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponse>> update(@PathVariable Long id,
                                                            @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success(bookService.update(id, request), "Book updated"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Book deleted"));
    }
}