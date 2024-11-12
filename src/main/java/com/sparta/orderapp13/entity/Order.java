package com.sparta.orderapp13.entity;

import com.sparta.orderapp13.dto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID orderId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToMany(mappedBy = "order")
    private List<StoreOrder> storeOrderList = new ArrayList<>();

//    @OneToOne(mappedBy = "order")
//    private Payment payment;

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

//    public Order(OrderRequestDto requestDto, UUID store, int totalPrice) {
//        this.orderId = UUID.randomUUID();
////        this.user = user;
//        try {
//            this.orderType = OrderType.valueOf(requestDto.getOrderType().toUpperCase());
//        } catch (IllegalArgumentException e) {
//            this.orderType = OrderType.ONLINE_ORDER;
//        }
//        this.orderStatus = OrderStatus.ORDER_PENDING;
//        this.orderInstructions = requestDto.getOrderInstructions();
//        this.deliveryAddress = requestDto.getDeliveryAddress();
//        this.deliveryInstructions = requestDto.getDeliveryInstructions();
//        this.totalPrice = totalPrice;
//        StoreOrder storeOrder = new StoreOrder(orderId, store.getStoreId());
//        this.storeOrderList.add(storeOrder);
//    }


}
