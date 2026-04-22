package co.edu.ustavillavo.desemp.taller_bookstore.repository;

import co.edu.ustavillavo.desemp.taller_bookstore.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"items", "items.book"})
    List<Order> findByUserEmail(String userEmail);

    @EntityGraph(attributePaths = {"items", "items.book"})
    Optional<Order> findById(Long id);
}