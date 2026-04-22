package co.edu.ustavillavo.desemp.taller_bookstore.service;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.CategoryRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    CategoryResponse findById(Long id);
    List<CategoryResponse> findAll();
    CategoryResponse update(Long id, CategoryRequest request);
    void delete(Long id);
    Page<BookResponse> findBooksByCategory(Long categoryId, Pageable pageable);
}