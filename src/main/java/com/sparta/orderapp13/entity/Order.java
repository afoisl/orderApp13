package com.sparta.orderapp13.entity;

import com.sparta.orderapp13.dto.OrderRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import javax.crypto.Mac;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "p_orders")
public class Order {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderFood> orderFoodList = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<StoreOrder> storeOrderList = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderType orderType = OrderType.ONLINE_ORDER;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDER_PENDING;

    @Column
    @Size(max = 200)
    private String orderInstructions;

    @Column
    private int totalPrice;

    @Column
    private String deliveryAddress;

    @Column
    @Size(max = 200)
    private String deliveryInstructions;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private String createdBy;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private String updatedBy;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private String deletedBy;

    public Order(OrderRequestDto requestDto, int totalPrice, User user) {
        this.orderId = UUID.randomUUID();
        this.user = user;
        try {
            this.orderType = OrderType.valueOf(requestDto.getOrderType().toUpperCase());
        } catch (IllegalArgumentException e) {
            this.orderType = OrderType.ONLINE_ORDER;
        }
        this.orderStatus = OrderStatus.ORDER_PENDING;
        this.orderInstructions = requestDto.getOrderInstructions();
        this.deliveryAddress = requestDto.getDeliveryAddress();
        this.deliveryInstructions = requestDto.getDeliveryInstructions();
        this.totalPrice = totalPrice;
    }

    public void cancel() {
        this.orderStatus = OrderStatus.ORDER_CANCEL;
    }

    public void complete() {
        this.orderStatus = OrderStatus.ORDER_COMPLETED;
    }

    public void delivering() {
        this.orderStatus = OrderStatus.ORDER_DELIVERING;
    }

    public void confirm() {
        this.orderStatus = OrderStatus.ORDER_CONFIRMED;
    }
}
