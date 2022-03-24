package com.example.codingtest.controller;

import com.example.codingtest.entity.Order;
import com.example.codingtest.entity.OrderMapping;
import com.example.codingtest.enumeration.Currency;
import com.example.codingtest.enumeration.MatchStatus;
import com.example.codingtest.enumeration.OrderType;
import com.example.codingtest.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderController orderController;


    @Test
    void createOrder_testPositiveFlow() {
        Currency from = Currency.GBP;
        Currency to = Currency.USD;
        double amount = 100;
        String expectedOrderId = "1";

        when(orderService.create(Order.to(from, to, amount, OrderType.BUY))).thenReturn(expectedOrderId);
        String orderId = orderController.createOrder(from, to, amount, OrderType.BUY);
        Assertions.assertEquals(expectedOrderId, orderId);
    }

    @Test
    void createOrder_testExceptionFlow() {
        Currency from = Currency.GBP;
        Currency to = Currency.GBP;
        Exception thrown = Assertions.assertThrows(RuntimeException.class, () -> orderController.createOrder(from, to, 100.0, OrderType.BUY));
        Assertions.assertEquals("Currency can not be converted to same currency!", thrown.getMessage());
    }

    @Test
    void checkStatus_testPositiveFlow() {
        long orderId = 1L;
        String expectedStatus = MatchStatus.PENDING.toString();
        when(orderService.status(orderId)).thenReturn(expectedStatus);
        String status = orderController.checkStatus(orderId);
        Assertions.assertEquals(expectedStatus, status);
    }

    @Test
    void allMappings_testPositiveFlow() {
        when(orderService.allMappings(any())).thenReturn(List.of(OrderMapping.builder().build()));
        List<OrderMapping> mappings = orderController.allMappings(1L);
        Assertions.assertEquals(1, mappings.size());
    }
}