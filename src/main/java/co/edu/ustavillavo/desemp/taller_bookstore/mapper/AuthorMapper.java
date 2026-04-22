package co.edu.ustavillavo.desemp.taller_bookstore.mapper;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.AuthorRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.AuthorResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequest request) {
        return Author.builder()
                .name(request.getName())
                .biography(request.getBiography())
                .email(request.getEmail())
                .build();
    }

    public AuthorResponse toResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .biography(author.getBiography())
                .email(author.getEmail())
                .build();
    }
}