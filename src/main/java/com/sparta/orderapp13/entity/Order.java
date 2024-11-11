package com.sparta.orderapp13.entity;

import com.sparta.orderapp13.dto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private UUID storeId;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @Column
    @Enumerated
    private OrderType orderType;

    @Column
    @Enumerated
    private OrderStatus orderStatus;

    private String orderInstructions;

    private int totalPrice;

    private String deliveryAddress;

    private String deliveryInstructions;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private LocalDateTime deletedAt;

    private String deletedBy;

    public Order(OrderRequestDto requestDto) {
        this.user = user;
        this.
    }
}
