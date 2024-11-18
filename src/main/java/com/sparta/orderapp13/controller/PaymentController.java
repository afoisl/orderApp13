package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.PaymentRequestDto;
import com.sparta.orderapp13.dto.PaymentResponseDto;
import com.sparta.orderapp13.security.UserDetailsImpl;
import com.sparta.orderapp13.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 생성
    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDto> create(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto paymentResponseDto = paymentService.create(paymentRequestDto);
        return ResponseEntity.ok(paymentResponseDto);
    }

    // 결제 전체 목록 조회
    @GetMapping("/payments/all")
    public List<PaymentResponseDto> getAll(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return paymentService.getAll(userDetails.getUser());
    }

    // 결제 전체 목록 조회 (관리자용)
    @GetMapping("/payments/admin")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    public List<PaymentResponseDto> getAllByAdmin(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return paymentService.getAllByAdmin(userDetails.getUser());
    }

    // 결제 상세 조회
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<PaymentResponseDto> get(@PathVariable UUID paymentId) {
        PaymentResponseDto paymentResponseDto = paymentService.get(paymentId);
        return ResponseEntity.ok(paymentResponseDto);
    }

    // 결제 취소 (상태 변경)
    @PatchMapping("/payment/{paymentId}")
    public ResponseEntity<String> cancel(@PathVariable UUID paymentId) {
        paymentService.cancel(paymentId);
        return ResponseEntity.ok("결제가 취소되었습니다.");
    }
}
