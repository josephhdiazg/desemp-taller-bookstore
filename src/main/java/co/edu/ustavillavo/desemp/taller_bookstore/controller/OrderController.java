package co.edu.ustavillavo.desemp.taller_bookstore.controller;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.OrderRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.ApiResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.OrderResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<OrderResponse>> create(@Valid @RequestBody OrderRequest request,
                                                             Principal principal) {
        OrderResponse response = orderService.create(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Order created", 201));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> myOrders(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(orderService.findMyOrders(principal.getName()), "Your orders"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(orderService.findAll(), "All orders"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<OrderResponse>> findById(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(orderService.findById(id, principal.getName()), "Order found"));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<OrderResponse>> cancel(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(orderService.cancel(id, principal.getName()), "Order cancelled"));
    }
}