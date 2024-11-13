package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.OrderRequestDto;
import com.sparta.orderapp13.dto.OrderResponseDto;
import com.sparta.orderapp13.dto.OrderUpdateDto;
import com.sparta.orderapp13.entity.Order;
import com.sparta.orderapp13.entity.OrderFood;
import com.sparta.orderapp13.entity.OrderStatus;
import com.sparta.orderapp13.entity.User;
import com.sparta.orderapp13.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        // 음식 총 가격
        int totalPrice = calculateTotalPrice(requestDto.getFoodList());

        // Order 객체 생성
        Order order = orderRepository.save(new Order(requestDto, totalPrice));

        return new OrderResponseDto(order);
    }

//    public Page<OrderResponseDto> getOrders(User user, int page, int size, String sortBy, boolean isAsc) {
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Role role = role.getRole();
//
//        Page<Order> orderList;
//
//        if (role == Role.USER) {
//            orderList = orderRepository.findAllByUser(user, pageable);
//        } else {
//            orderList = orderRepository.findAll(pageable);
//        }
//        return orderList.map(order -> new OrderResponseDto());
//    }

    @Transactional
    public OrderUpdateDto confirmOrder(OrderUpdateDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(EntityNotFoundException::new);

        // 새로운 주문 상태 가져오기
        try {
            OrderStatus newStatus = OrderStatus.valueOf(requestDto.getOrderStatus());

            // 현재 주문 상태가 이미 완료된 경우 상태 변경 불가
            if (newStatus == OrderStatus.ORDER_COMPLETED) {
                throw new IllegalArgumentException("이미 완료된 주문은 상태를 변경할 수 없습니다.");
            }

            // 주문 상태 업데이트
            order.confirm();


        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 주문 상태입니다.");
        }

        // 변경된 주문 저장 후 응답 DTO 반환
        orderRepository.save(order);
        return new OrderUpdateDto(order);
    }

    @Transactional
    public OrderUpdateDto deliveringOrder(OrderUpdateDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(EntityNotFoundException::new);

        // 새로운 주문 상태 가져오기
        try {
            OrderStatus newStatus = OrderStatus.valueOf(requestDto.getOrderStatus());

            // 현재 주문 상태가 이미 완료된 경우 상태 변경 불가
            if (newStatus == OrderStatus.ORDER_COMPLETED) {
                throw new IllegalArgumentException("이미 완료된 주문은 상태를 변경할 수 없습니다.");
            }

            // 주문 상태 업데이트
            order.delivering();


        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 주문 상태입니다.");
        }

        // 변경된 주문 저장 후 응답 DTO 반환
        orderRepository.save(order);
        return new OrderUpdateDto(order);
    }

    @Transactional
    public OrderUpdateDto completeOrder(OrderUpdateDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(EntityNotFoundException::new);

        // 새로운 주문 상태 가져오기
        try {
            OrderStatus newStatus = OrderStatus.valueOf(requestDto.getOrderStatus());

            // 현재 주문 상태가 이미 완료된 경우 상태 변경 불가
            if (newStatus == OrderStatus.ORDER_CONFIRMED) {
                throw new IllegalArgumentException("이미 완료된 주문은 상태를 변경할 수 없습니다.");
            }

            // 주문 상태 업데이트
            order.complete();


        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 주문 상태입니다.");
        }

        // 변경된 주문 저장 후 응답 DTO 반환
        orderRepository.save(order);
        return new OrderUpdateDto(order);
    }

    @Transactional
    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        LocalDateTime createdTime = order.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();

        // 5분이 지난 경우 취소 불가
        if (createdTime.plusMinutes(5).isBefore(now)) {
            throw new IllegalArgumentException("주문을 취소할 수 없습니다.");
        }

        // 주문 상태를 취소로 변경
        order.cancel();

        orderRepository.save(order);
    }

    public int calculateTotalPrice(List<OrderFood> foodList) {
        int totalPrice = 0;
        for (OrderFood orderFood : foodList) {
            totalPrice = orderFood.getFood().getFoodPrice() * orderFood.getQuantity();
        }
        return totalPrice;
    }
}
