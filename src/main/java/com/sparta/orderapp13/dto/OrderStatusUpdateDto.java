package com.sparta.orderapp13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusUpdateDto {

    private UUID orderId;
    private String userId;
    private String newOrderStatus;
}
