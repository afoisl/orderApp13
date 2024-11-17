package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "p_store_order")
public class StoreOrder {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID storeOrderId;

    @ManyToOne
    @JoinColumn
    private Order order;

    @ManyToOne
    @JoinColumn
    private Store store;

    public StoreOrder(UUID orderId, UUID storeId) {
    }
}
