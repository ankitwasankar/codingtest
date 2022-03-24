package com.example.codingtest.repository;

import com.example.codingtest.entity.Order;
import com.example.codingtest.entity.OrderMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMappingRepository extends JpaRepository<OrderMapping, Long> {

    List<OrderMapping> findByOrderOrOrderMatch(Order order, Order orderMatch);
}
