package com.example.codingtest.service;

import com.example.codingtest.entity.Order;
import com.example.codingtest.entity.OrderMapping;
import com.example.codingtest.repository.OrderMappingRepository;
import com.example.codingtest.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderMatchingService orderMatchingService;
    @Autowired
    private final OrderMappingRepository orderMappingRepository;


    public String create(Order order) {
        var savedOrder = orderRepository.save(order);
        orderMatchingService.match(savedOrder);
        return String.valueOf(savedOrder.getId());
    }


    public String status(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(o -> o.getStatus().toString()).orElse("Order not found!");
    }

    public List<OrderMapping> allMappings(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return orderMappingRepository.findByOrderOrOrderMatch(order.get(), order.get());
        }
        return Collections.emptyList();
    }
}
