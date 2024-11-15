package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.PaymentRequestDto;
import com.sparta.orderapp13.dto.PaymentResponseDto;
import com.sparta.orderapp13.security.UserDetailsImpl;
import com.sparta.orderapp13.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments")
    public PaymentResponseDto create(@RequestBody PaymentRequestDto paymentRequestDto) {
        return paymentService.create(paymentRequestDto);
    }

    @GetMapping("/payments/all")
    public List<PaymentResponseDto> getAll(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return paymentService.getAll(userDetails.getUser());
    }
}
