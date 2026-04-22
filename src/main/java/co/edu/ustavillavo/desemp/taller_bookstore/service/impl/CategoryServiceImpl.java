package co.edu.ustavillavo.desemp.taller_bookstore.service.impl;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.CategoryRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.BookResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.CategoryResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Category;
import co.edu.ustavillavo.desemp.taller_bookstore.exception.custom.DuplicateResourceException;
import co.edu.ustavillavo.desemp.taller_bookstore.exception.custom.ResourceNotFoundException;
import co.edu.ustavillavo.desemp.taller_bookstore.mapper.BookMapper;
import co.edu.ustavillavo.desemp.taller_bookstore.mapper.CategoryMapper;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.BookRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.CategoryRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               BookRepository bookRepository,
                               CategoryMapper categoryMapper,
                               BookMapper bookMapper) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.categoryMapper = categoryMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category", "name", request.getName());
        }
        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        return categoryMapper.toResponse(getCategoryOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getCategoryOrThrow(id);
        if (!category.getName().equals(request.getName())
                && categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category", "name", request.getName());
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        getCategoryOrThrow(id);
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> findBooksByCategory(Long categoryId, Pageable pageable) {
        getCategoryOrThrow(categoryId);
        return bookRepository.findByCategoryId(categoryId, pageable)
                .map(bookMapper::toResponse);
    }

    private Category getCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }
}