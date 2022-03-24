package com.example.codingtest.controller;

import com.example.codingtest.entity.Order;
import com.example.codingtest.entity.OrderMapping;
import com.example.codingtest.enumeration.Currency;
import com.example.codingtest.enumeration.OrderType;
import com.example.codingtest.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/order")
@AllArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    public String createOrder(@NonNull Currency from, @NonNull Currency to, @NonNull Double amount, @NonNull OrderType type) {
        return orderService.create(Order.to(from, to, amount, type));
    }

    @GetMapping
    public String checkStatus(@NonNull Long orderId) {
        return orderService.status(orderId);
    }

    @GetMapping("/mapping")
    public List<OrderMapping> allMappings(@NonNull Long orderId) {
        return orderService.allMappings(orderId);
    }

}
