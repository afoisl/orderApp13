package com.sparta.orderapp13.dto;

import com.sparta.orderapp13.entity.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class PaymentResponseDto {

    private UUID orderId;
    private int paymentAmount;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime paymentDate;

    public PaymentResponseDto(Payment payment) {
        this.orderId = payment.getOrder().getOrderId();
        this.paymentAmount = payment.getPaymentAmount();
        this.paymentMethod = String.valueOf(payment.getPaymentMethod());
        this.paymentStatus = String.valueOf(payment.getPaymentStatus());
        LocalDateTime now = LocalDateTime.now();
        this.paymentDate = now.withSecond(0).withNano(0);
    }
}
