package com.example.Food_Ordering.controller;

import com.example.Food_Ordering.dto.OrderDTO;
import com.example.Food_Ordering.entity.Product;
import com.example.Food_Ordering.enums.OrderStatus;
import com.example.Food_Ordering.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderServices;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderServices.createOrder(orderDTO));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable UUID orderId,
                                                @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderServices.updateOrder(orderId, orderDTO));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderServices.getOrderById(orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderServices.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAllOrders(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderServices.getAllOrder(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<OrderDTO>> getOrdersByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderServices.getAllOrderByStatus(status));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable UUID customerId) {
        return ResponseEntity.ok(orderServices.getOrderByCustomerId(customerId));
    }


    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable UUID orderId,
                                                      @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderServices.updateOrderStatus(orderId, status));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId) {
        orderServices.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/products")
    public ResponseEntity<OrderDTO> addProductsToOrder(@PathVariable UUID orderId,
                                                       @RequestBody List<Product> products) {
        return ResponseEntity.ok(orderServices.addProductsToOrder(orderId, products));
    }

    @DeleteMapping("/{orderId}/products")
    public ResponseEntity<OrderDTO> removeProductsFromOrder(@PathVariable UUID orderId,
                                                            @RequestBody List<UUID> productIds) {
        return ResponseEntity.ok(orderServices.deleteProductsFormOrder(orderId, productIds));
    }

    @GetMapping("/{orderId}/products")
    public ResponseEntity<List<Product>> getProductsByOrderId(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderServices.getProductsByOrderId(orderId));
    }
}
