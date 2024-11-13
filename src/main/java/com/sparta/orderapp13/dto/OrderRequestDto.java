package com.sparta.orderapp13.dto;

import com.sparta.orderapp13.entity.OrderFood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequestDto {

    private String userId;
    private UUID storeId;
    private List<OrderFood> foodList;
    private String paymentMethod;
    private String orderType;
    private String orderInstructions;
    private String deliveryAddress;
    private String deliveryInstructions;
}
