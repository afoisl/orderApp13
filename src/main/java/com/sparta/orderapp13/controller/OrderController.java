package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.OrderRequestDto;
import com.sparta.orderapp13.dto.OrderResponseDto;
import com.sparta.orderapp13.dto.OrderUpdateDto;
import com.sparta.orderapp13.security.UserDetailsImpl;
import com.sparta.orderapp13.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto) {
        return orderService.createOrder(requestDto);
    }

    @GetMapping("/orders")
    public Page<OrderResponseDto> getOrders(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        return orderService.getOrders(userDetails.getUser(),page - 1, size, sortBy, isAsc);
    }

    @PatchMapping("/order/confirm")
    public OrderUpdateDto confirmOrder(@RequestBody OrderUpdateDto requestDto) {
        return orderService.confirmOrder(requestDto);
    }

    @PatchMapping("/order/delivering")
    public OrderUpdateDto deliveringOrder(@RequestBody OrderUpdateDto requestDto) {
        return orderService.deliveringOrder(requestDto);
    }

    @PatchMapping("/order/complete")
    public OrderUpdateDto completeOrder(@RequestBody OrderUpdateDto requestDto) {
        return orderService.completeOrder(requestDto);
    }

    @PatchMapping("/order/cancel/{orderId}")
    public void cancelOrder(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);
    }
}
