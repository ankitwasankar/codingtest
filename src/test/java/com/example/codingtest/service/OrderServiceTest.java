package com.example.codingtest.service;

import com.example.codingtest.entity.Order;
import com.example.codingtest.enumeration.Currency;
import com.example.codingtest.enumeration.MatchStatus;
import com.example.codingtest.enumeration.OrderType;
import com.example.codingtest.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderMatchingService orderMatchingService;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order buyOrder = Order.builder()
            .type(OrderType.BUY)
            .amount(100.00)
            .from(Currency.GBP)
            .to(Currency.USD)
            .id(1L)
            .status(MatchStatus.FULLY_MATCHED)
            .build();

    @Test
    void testCreate_positiveFlow() {
        Mockito.when(orderRepository.save(buyOrder)).thenReturn(buyOrder);
        String order = orderService.create(buyOrder);
        Assertions.assertEquals(order, String.valueOf(buyOrder.getId()));
    }

    @Test
    void testStatus_positiveFlow() {
        Mockito.when(orderRepository.findById(any())).thenReturn(Optional.of(buyOrder));
        String status = orderService.status(1L);
        Assertions.assertEquals(MatchStatus.FULLY_MATCHED.toString(), status);
    }

}