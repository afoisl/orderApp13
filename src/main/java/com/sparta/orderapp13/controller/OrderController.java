package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.OrderRequestDto;
import com.sparta.orderapp13.dto.OrderResponseDto;
import com.sparta.orderapp13.dto.OrderUpdateDto;
import com.sparta.orderapp13.security.UserDetailsImpl;
import com.sparta.orderapp13.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public OrderResponseDto creatOrder(@RequestBody OrderRequestDto requestDto) {
        return orderService.createOrder(requestDto);
    }

    // 유저별로 주문 전체 목록 조회
    @GetMapping("/orders")
    public Page<OrderResponseDto> getOrders(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        return orderService.getOrders(userDetails.getUser(),page - 1, size, sortBy, isAsc);
    }

    // 전체 주문 목록 조회 (관리자용)
    @GetMapping("/orders/admin")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    public Page<OrderResponseDto> getOrdersByAdmin(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return orderService.getOrdersByAdmin(userDetails.getUser(),page - 1, size, sortBy, isAsc);
    }

    // 주문 확인 (상태 변경)
    @PatchMapping("/order/confirm")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public OrderUpdateDto confirmOrder(@RequestBody OrderUpdateDto requestDto) {
        return orderService.confirmOrder(requestDto);
    }

    // 주문중 (상태 변경)
    @PatchMapping("/order/delivering")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public OrderUpdateDto deliveringOrder(@RequestBody OrderUpdateDto requestDto) {
        return orderService.deliveringOrder(requestDto);
    }

    // 주문 완료 (상태 변경)
    @PatchMapping("/order/complete")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public OrderUpdateDto completeOrder(@RequestBody OrderUpdateDto requestDto) {
        return orderService.completeOrder(requestDto);
    }

    // 주문 취소 (상태 변경)
    @PatchMapping("/order/cancel/{orderId}")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public void cancelOrder(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);
    }
}
