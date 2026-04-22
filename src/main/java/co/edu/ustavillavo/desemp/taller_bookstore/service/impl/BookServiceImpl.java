package co.edu.ustavillavo.desemp.taller_bookstore.service.impl;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.BookRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Author;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Book;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Category;
import co.edu.ustavillavo.desemp.taller_bookstore.exception.custom.DuplicateResourceException;
import co.edu.ustavillavo.desemp.taller_bookstore.exception.custom.ResourceNotFoundException;
import co.edu.ustavillavo.desemp.taller_bookstore.mapper.BookMapper;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.AuthorRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.BookRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.CategoryRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           CategoryRepository categoryRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookResponse create(BookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("Book", "isbn", request.getIsbn());
        }
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", request.getAuthorId()));
        List<Category> categories = resolveCategories(request.getCategoryIds());
        Book saved = bookRepository.save(bookMapper.toEntity(request, author, categories));
        return bookMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        return bookMapper.toResponse(getBookOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> findByAuthor(Long authorId, Pageable pageable) {
        return bookRepository.findByAuthorId(authorId, pageable).map(bookMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> findByCategory(Long categoryId, Pageable pageable) {
        return bookRepository.findByCategoryId(categoryId, pageable).map(bookMapper::toResponse);
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book book = getBookOrThrow(id);
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("Book", "isbn", request.getIsbn());
        }
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", request.getAuthorId()));
        List<Category> categories = resolveCategories(request.getCategoryIds());
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());
        book.setDescription(request.getDescription());
        book.setAuthor(author);
        book.setCategories(categories);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        getBookOrThrow(id);
        bookRepository.deleteById(id);
    }

    private List<Category> resolveCategories(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        return ids.stream()
                .map(cid -> categoryRepository.findById(cid)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", cid)))
                .toList();
    }

    private Book getBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));
    }
}