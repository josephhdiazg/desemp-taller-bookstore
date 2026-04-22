package co.edu.ustavillavo.desemp.taller_bookstore.controller;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.AuthorRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.ApiResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.AuthorResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AuthorResponse>> create(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse response = authorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Author created successfully", 201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(authorService.findById(id), "Author found"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(authorService.findAll(), "Authors retrieved"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AuthorResponse>> update(@PathVariable Long id,
                                                              @Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authorService.update(id, request), "Author updated"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Author deleted"));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> findBooks(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(authorService.findBooksByAuthor(id, pageable), "Books by author"));
    }
}