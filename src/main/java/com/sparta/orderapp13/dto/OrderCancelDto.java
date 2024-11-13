package com.sparta.orderapp13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderCancelDto {
    private UUID orderId;
    private String userId;
}
