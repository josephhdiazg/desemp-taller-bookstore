package co.edu.ustavillavo.desemp.taller_bookstore.repository;

import co.edu.ustavillavo.desemp.taller_bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId")
    Page<Book> findByAuthorId(@Param("authorId") Long authorId, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    Page<Book> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    boolean existsByAuthorId(Long authorId);
}
