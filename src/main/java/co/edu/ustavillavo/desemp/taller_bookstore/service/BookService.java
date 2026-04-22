package co.edu.ustavillavo.desemp.taller_bookstore.service;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.BookRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse create(BookRequest request);
    BookResponse findById(Long id);
    Page<BookResponse> findAll(Pageable pageable);
    Page<BookResponse> findByAuthor(Long authorId, Pageable pageable);
    Page<BookResponse> findByCategory(Long categoryId, Pageable pageable);
    BookResponse update(Long id, BookRequest request);
    void delete(Long id);
}
