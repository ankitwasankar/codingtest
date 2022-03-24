package com.example.codingtest.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDERS_MAPPING")
public class OrderMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID_MATCH", nullable = false)
    private Order orderMatch;

    private Double amount;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

}
