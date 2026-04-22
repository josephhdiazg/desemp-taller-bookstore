package co.edu.ustavillavo.desemp.taller_bookstore.service.impl;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.OrderItemRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.OrderRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.OrderResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.*;
import co.edu.ustavillavo.desemp.taller_bookstore.exception.custom.*;
import co.edu.ustavillavo.desemp.taller_bookstore.mapper.OrderMapper;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.BookRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.OrderRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            BookRepository bookRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse create(OrderRequest request, String userEmail) {
        Order order = Order.builder()
                .userEmail(userEmail)
                .status(OrderStatus.PENDING)
                .items(new ArrayList<>())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Book book = bookRepository.findById(itemRequest.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book", itemRequest.getBookId()));

            if (book.getStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException(book.getTitle(), book.getStock(), itemRequest.getQuantity());
            }

            book.setStock(book.getStock() - itemRequest.getQuantity());
            bookRepository.save(book);

            BigDecimal subtotal = book.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .book(book)
                    .quantity(itemRequest.getQuantity())
                    .subtotal(subtotal)
                    .build();

            order.getItems().add(item);
            total = total.add(subtotal);
        }

        order.setTotal(total);
        order.setStatus(OrderStatus.CONFIRMED);

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findMyOrders(String userEmail) {
        return orderRepository.findByUserEmail(userEmail).stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findById(Long id, String userEmail) {
        Order order = getOrderOrThrow(id);
        if (!order.getUserEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException();
        }
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse cancel(Long id, String userEmail) {
        Order order = getOrderOrThrow(id);
        if (!order.getUserEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException();
        }
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new InvalidOrderStateException(order.getStatus().name(), "cancel");
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toResponse(orderRepository.save(order));
    }

    private Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }
}