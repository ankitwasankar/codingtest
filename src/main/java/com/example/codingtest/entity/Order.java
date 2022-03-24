package com.example.codingtest.entity;

import com.example.codingtest.enumeration.Currency;
import com.example.codingtest.enumeration.MatchStatus;
import com.example.codingtest.enumeration.OrderType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Builder
@AllArgsConstructor
@Table(name = "ORDERS")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_FROM")
    private Currency from;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_TO")
    private Currency to;

    private Double amount;

    private Double pending;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDate createdAt;


    public static Order to(Currency from, Currency to, Double amount, OrderType type) {
        if (from == to) {
            throw new RuntimeException("Currency can not be converted to same currency!");
        }
        return Order.builder()
                .from(from)
                .to(to)
                .amount(amount)
                .createdAt(LocalDate.now())
                .status(MatchStatus.PENDING)
                .pending(amount)
                .type(type)
                .build();
    }
}
