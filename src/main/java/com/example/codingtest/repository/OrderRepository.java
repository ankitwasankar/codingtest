package com.example.codingtest.repository;

import com.example.codingtest.entity.Order;
import com.example.codingtest.enumeration.Currency;
import com.example.codingtest.enumeration.MatchStatus;
import com.example.codingtest.enumeration.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByToAndTypeAndStatusInOrderByCreatedAt(Currency to, OrderType type, List<MatchStatus> matchStatus);

}
