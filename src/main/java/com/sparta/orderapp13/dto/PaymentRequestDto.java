package com.sparta.orderapp13.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class PaymentRequestDto {

    private UUID orderId;
    private int paymentAmount;
    private String paymentMethod;
    private String paymentStatus;
}
