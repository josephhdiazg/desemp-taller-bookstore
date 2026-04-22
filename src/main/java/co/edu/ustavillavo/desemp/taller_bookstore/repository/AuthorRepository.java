package co.edu.ustavillavo.desemp.taller_bookstore.repository;

import co.edu.ustavillavo.desemp.taller_bookstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByEmail(String email);
}