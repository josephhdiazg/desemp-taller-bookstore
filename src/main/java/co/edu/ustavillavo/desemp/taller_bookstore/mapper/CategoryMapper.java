package co.edu.ustavillavo.desemp.taller_bookstore.mapper;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.CategoryRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.CategoryResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}