package com.example.Food_Ordering.service.implentation;

import com.example.Food_Ordering.dto.OrderDTO;
import com.example.Food_Ordering.entity.Product;
import com.example.Food_Ordering.enums.OrderStatus;
import com.example.Food_Ordering.repository.OrderRepository;
import com.example.Food_Ordering.repository.ProductRepository;
import com.example.Food_Ordering.repository.UserRepository;
import com.example.Food_Ordering.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO getOrderById(UUID orderId) {
        return null;
    }

    @Override
    public OrderDTO updateOrder(UUID orderId, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        return null;
    }

    @Override
    public OrderDTO addProductsToOrder(UUID orderId, List<Product> products) {
        return null;
    }

    @Override
    public OrderDTO deleteProductsFormOrder(UUID orderId, List<UUID> productIds) {
        return null;
    }

    @Override
    public Page<OrderDTO> getAllOrder(Pageable pageable) {
        return null;
    }

    @Override
    public Page<OrderDTO> getAllOrderByStatus(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public List<OrderDTO> getOrderByCustomerId(UUID customerId) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByOrderId(UUID orderId) {
        return List.of();
    }

    @Override
    public void deleteOrder(UUID orderId) {

    }

    @Override
    public void cancelOrder(UUID orderNumber) {

    }
}
