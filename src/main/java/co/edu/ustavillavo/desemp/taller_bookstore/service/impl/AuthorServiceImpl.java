package co.edu.ustavillavo.desemp.taller_bookstore.service.impl;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.AuthorRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.AuthorResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Author;
import co.edu.ustavillavo.desemp.taller_bookstore.exception.custom.AuthorHasBooksException;
import co.edu.ustavillavo.desemp.taller_bookstore.exception.custom.DuplicateResourceException;
import co.edu.ustavillavo.desemp.taller_bookstore.exception.custom.ResourceNotFoundException;
import co.edu.ustavillavo.desemp.taller_bookstore.mapper.AuthorMapper;
import co.edu.ustavillavo.desemp.taller_bookstore.mapper.BookMapper;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.AuthorRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.BookRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             BookRepository bookRepository,
                             AuthorMapper authorMapper,
                             BookMapper bookMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public AuthorResponse create(AuthorRequest request) {
        if (request.getEmail() != null && authorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Author", "email", request.getEmail());
        }
        Author saved = authorRepository.save(authorMapper.toEntity(request));
        return authorMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorResponse findById(Long id) {
        return authorMapper.toResponse(getAuthorOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponse> findAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .toList();
    }

    @Override
    public AuthorResponse update(Long id, AuthorRequest request) {
        Author author = getAuthorOrThrow(id);
        if (request.getEmail() != null
                && !request.getEmail().equals(author.getEmail())
                && authorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Author", "email", request.getEmail());
        }
        author.setName(request.getName());
        author.setBiography(request.getBiography());
        author.setEmail(request.getEmail());
        return authorMapper.toResponse(authorRepository.save(author));
    }

    @Override
    public void delete(Long id) {
        getAuthorOrThrow(id);
        if (bookRepository.existsByAuthorId(id)) {
            throw new AuthorHasBooksException(id);
        }
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> findBooksByAuthor(Long authorId, Pageable pageable) {
        getAuthorOrThrow(authorId);
        return bookRepository.findByAuthorId(authorId, pageable)
                .map(bookMapper::toResponse);
    }

    private Author getAuthorOrThrow(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", id));
    }
}
