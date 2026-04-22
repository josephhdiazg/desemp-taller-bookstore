package co.edu.ustavillavo.desemp.taller_bookstore.mapper;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.BookRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Author;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Book;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Category;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final CategoryMapper categoryMapper;

    public BookMapper(AuthorMapper authorMapper, CategoryMapper categoryMapper) {
        this.authorMapper = authorMapper;
        this.categoryMapper = categoryMapper;
    }

    public Book toEntity(BookRequest request, Author author, List<Category> categories) {
        return Book.builder()
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .stock(request.getStock())
                .description(request.getDescription())
                .author(author)
                .categories(categories)
                .build();
    }

    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .stock(book.getStock())
                .description(book.getDescription())
                .author(authorMapper.toResponse(book.getAuthor()))
                .categories(book.getCategories().stream()
                        .map(categoryMapper::toResponse)
                        .toList())
                .build();
    }
}