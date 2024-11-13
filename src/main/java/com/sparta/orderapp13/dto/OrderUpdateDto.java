package com.sparta.orderapp13.dto;

import com.sparta.orderapp13.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderUpdateDto {

    private UUID orderId;
    private String userId;
    private String orderStatus;

    public OrderUpdateDto(Order order) {
        this.orderId = order.getOrderId();
//        this.userId = order.getUser().getUserId();
        this.orderStatus = String.valueOf(order.getOrderStatus());
    }
}
