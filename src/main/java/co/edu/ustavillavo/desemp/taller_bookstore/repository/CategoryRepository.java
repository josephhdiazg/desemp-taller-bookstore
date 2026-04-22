package co.edu.ustavillavo.desemp.taller_bookstore.repository;

import co.edu.ustavillavo.desemp.taller_bookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
