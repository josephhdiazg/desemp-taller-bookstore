package co.edu.ustavillavo.desemp.taller_bookstore.service;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.OrderRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.OrderResponse;
import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request, String userEmail);
    List<OrderResponse> findMyOrders(String userEmail);
    List<OrderResponse> findAll();
    OrderResponse findById(Long id, String userEmail);
    OrderResponse cancel(Long id, String userEmail);
}