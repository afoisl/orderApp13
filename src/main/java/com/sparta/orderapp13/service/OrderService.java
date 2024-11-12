package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.OrderRequestDto;
import com.sparta.orderapp13.dto.OrderResponseDto;
import com.sparta.orderapp13.entity.Order;
import com.sparta.orderapp13.entity.OrderStatus;
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
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

//    @Transactional
//    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
//
//        // storeId 가져오기
//        UUID storeId = storeRepository.findById(requestDto.getStoreId())
//                .orElseThrow(() -> new RuntimeException("해당 가게가 존재하지 않습니다."));
//
//        // 음식 총 가격
//        int totalPrice = calculateTotalPrice(requestDto.getFoodList());
//
//        // Order 객체 생성
//        Order order = new Order(requestDto, storeId, totalPrice);
//        orderRepository.save(order);
//
//        return new OrderResponseDto(order, storeId);
//    }
//
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
//
//    @Transactional
//    public OrderResponseDto updateOrder(Order orderId, OrderRequestDto requestDto) {
//        Order order = orderRepository.findById(orderId.getOrderId())
//                .orElseThrow(EntityNotFoundException::new);
//
//        // 새로운 주문 상태 가져오기
//        try {
//            OrderStatus newStatus = OrderStatus.valueOf(String.valueOf(order.getOrderStatus()));
//
//            // 현재 주문 상태가 이미 완료된 경우 상태 변경 불가
//            if (order.getOrderStatus() == OrderStatus.ORDER_CONFIRMED) {
//                throw new RuntimeException("이미 완료된 주문은 상태를 변경할 수 없습니다.");
//            }
//
//            // 주문 상태 업데이트
//
//
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException("유효하지 않은 주문 상태입니다.");
//        }
//
//        // 변경된 주문 저장 후 응답 DTO 반환
//        orderRepository.save(order);
//        return new OrderResponseDto(order);
//    }
//
//    @Transactional
//    public OrderResponseDto deleteOrder(UUID orderId, OrderRequestDto requestDto) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(EntityNotFoundException::new);
//
//        LocalDateTime createdTime = order.getCreatedAt();
//        LocalDateTime now = LocalDateTime.now();
//
//        // 5분이 지난 경우 취소 불가
//        if (createdTime.plusMinutes(5).isBefore(now)) {
//            throw new RuntimeException("주문을 취소할 수 없습니다.");
//        }
//
//        // 주문 상태를 취소로 변경
//
//
//        orderRepository.save(order);
//        return new OrderResponseDto(order);
//    }
//
//    public int calculateTotalPrice(List<orderFoodDto> foodList) {
//        for (Object o : foodList) {
//
//        }
//        return 0;
//    }
}
