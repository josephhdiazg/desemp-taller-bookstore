package co.edu.ustavillavo.desemp.taller_bookstore.mapper;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.OrderResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userEmail(order.getUserEmail())
                .status(order.getStatus())
                .total(order.getTotal())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(orderItemMapper::toResponse)
                        .toList())
                .build();
    }
}