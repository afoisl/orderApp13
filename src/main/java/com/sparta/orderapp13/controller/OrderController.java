package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.OrderRequestDto;
import com.sparta.orderapp13.dto.OrderResponseDto;
import com.sparta.orderapp13.dto.OrderUpdateDto;
import com.sparta.orderapp13.security.UserDetailsImpl;
import com.sparta.orderapp13.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> create(@RequestBody OrderRequestDto requestDto) {
        OrderResponseDto orderResponseDto = orderService.create(requestDto);
        return ResponseEntity.ok(orderResponseDto);
    }

    // 유저별로 주문 전체 목록 조회
    @GetMapping("/orders")
    public Page<OrderResponseDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        return orderService.getAll(userDetails.getUser(),page - 1, size, sortBy, isAsc);
    }

    // 전체 주문 목록 조회 (관리자용)
    @GetMapping("/orders/admin")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    public Page<OrderResponseDto> getAllByAdmin(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAse", defaultValue = "true") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return orderService.getAllByAdmin(userDetails.getUser(),page - 1, size, sortBy, isAsc);
    }

    // 주문 확인 (상태 변경)
    @PatchMapping("/order/confirm")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public ResponseEntity<OrderUpdateDto> confirm(@RequestBody OrderUpdateDto requestDto) {
        OrderUpdateDto orderUpdateDto = orderService.confirm(requestDto);
        return ResponseEntity.ok(orderUpdateDto);
    }

    // 주문중 (상태 변경)
    @PatchMapping("/order/delivering")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public ResponseEntity<OrderUpdateDto> delivering(@RequestBody OrderUpdateDto requestDto) {
        OrderUpdateDto orderUpdateDto = orderService.delivering(requestDto);
        return ResponseEntity.ok(orderUpdateDto);
    }

    // 주문 완료 (상태 변경)
    @PatchMapping("/order/complete")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public ResponseEntity<OrderUpdateDto> complete(@RequestBody OrderUpdateDto requestDto) {
        OrderUpdateDto orderUpdateDto = orderService.complete(requestDto);
        return ResponseEntity.ok(orderUpdateDto);
    }

    // 주문 취소 (상태 변경)
    @PatchMapping("/order/cancel/{orderId}")
    public ResponseEntity<String> cancel(@PathVariable UUID orderId) {
        orderService.cancel(orderId);
        return ResponseEntity.ok("주문이 취소되었습니다.");
    }
}
