package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.OrderRequestDto;
import com.sparta.orderapp13.dto.OrderResponseDto;
import com.sparta.orderapp13.entity.Order;
import com.sparta.orderapp13.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Order order = orderRepository.save(new Order(requestDto));
        return OrderResponseDto(new OrderResponseDto(order));
    }
}
