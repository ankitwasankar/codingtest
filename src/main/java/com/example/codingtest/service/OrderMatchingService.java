package com.example.codingtest.service;


import com.example.codingtest.entity.Order;
import com.example.codingtest.entity.OrderMapping;
import com.example.codingtest.enumeration.Currency;
import com.example.codingtest.enumeration.MatchStatus;
import com.example.codingtest.repository.OrderMappingRepository;
import com.example.codingtest.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderMatchingService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderMappingRepository orderMappingRepository;
    private static final double GBP_TO_USD = 2;
    private static final double USD_TO_GPB = 0.5;

    @Transactional
    public void match(Order order) {

        double multiplier = GBP_TO_USD;
        if (order.getFrom() == Currency.USD) {
            multiplier = USD_TO_GPB;
        }

        double amount = order.getPending() * multiplier;
        amount = Math.round(amount * 10) / 10.0;

        List<Order> ordersToMatch = orderRepository.findByToAndTypeAndStatusInOrderByCreatedAt(order.getFrom(), order.getType(),
                List.of(MatchStatus.PARTIALLY_MATCHED, MatchStatus.PENDING));
        for (Order orderToMatch : ordersToMatch) {
            double availableAmount = orderToMatch.getPending();

            if (availableAmount == amount) {
                orderRepository.save(updateToFullyMatched(order));
                orderRepository.save(updateToFullyMatched(orderToMatch));

                OrderMapping mapping = mapping(amount, order, orderToMatch);
                orderMappingRepository.save(mapping);
                break;
            } else if (availableAmount > amount) {
                orderRepository.save(updateToFullyMatched(order));

                double pending = availableAmount - amount;
                orderRepository.save(updateToPartiallyMatched(orderToMatch, pending));

                OrderMapping mapping = mapping(amount, order, orderToMatch);
                orderMappingRepository.save(mapping);
                break;

            } else {
                double pending = amount - availableAmount;
                amount = pending;
                pending = Math.round((amount / multiplier) * 10) / 10.0;

                orderRepository.save(updateToFullyMatched(orderToMatch));

                orderRepository.save(updateToPartiallyMatched(order, pending));

                OrderMapping mapping = mapping(availableAmount, orderToMatch, order);
                orderMappingRepository.save(mapping);

            }
        }
    }


    private Order updateToPartiallyMatched(Order order, double pending) {
        order.setStatus(MatchStatus.PARTIALLY_MATCHED);
        order.setPending(pending);
        return order;
    }

    private Order updateToFullyMatched(Order order) {
        order.setStatus(MatchStatus.FULLY_MATCHED);
        order.setPending(0.0);
        return order;
    }

    private OrderMapping mapping(double amount, Order order, Order matchedOrder) {
        return OrderMapping.builder()
                .amount(amount)
                .order(order)
                .orderMatch(matchedOrder)
                .createdAt(LocalDate.now())
                .build();
    }
}
