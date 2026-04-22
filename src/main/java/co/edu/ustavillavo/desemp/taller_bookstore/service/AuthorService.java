package co.edu.ustavillavo.desemp.taller_bookstore.service;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.AuthorRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.AuthorResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AuthorService {
    AuthorResponse create(AuthorRequest request);
    AuthorResponse findById(Long id);
    List<AuthorResponse> findAll();
    AuthorResponse update(Long id, AuthorRequest request);
    void delete(Long id);
    Page<BookResponse> findBooksByAuthor(Long authorId, Pageable pageable);
}