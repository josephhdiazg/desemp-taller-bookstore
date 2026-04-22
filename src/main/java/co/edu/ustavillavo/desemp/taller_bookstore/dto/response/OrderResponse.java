package co.edu.ustavillavo.desemp.taller_bookstore.dto.response;

import co.edu.ustavillavo.desemp.taller_bookstore.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private String userEmail;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}