package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.OrderFoodDto;
import com.sparta.orderapp13.dto.OrderRequestDto;
import com.sparta.orderapp13.dto.OrderResponseDto;
import com.sparta.orderapp13.dto.OrderUpdateDto;
import com.sparta.orderapp13.entity.*;
import com.sparta.orderapp13.repository.FoodRepository;
import com.sparta.orderapp13.repository.OrderFoodRepository;
import com.sparta.orderapp13.repository.OrderRepository;
import com.sparta.orderapp13.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderFoodRepository orderFoodRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        System.out.println(requestDto.getUserId());
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(()->
                new IllegalArgumentException("해당 하는 유저는 존재하지 않습니다."));

        // 음식 총 가격
        int totalPrice = calculateTotalPrice(requestDto.getFoodList());

        // Order 객체 생성
        Order order = orderRepository.save(new Order(requestDto, totalPrice, user));

        List<OrderFood> orderFoods = new ArrayList<>();

        for(OrderFoodDto orderFoodRequest : requestDto.getFoodList()){

            int quantity = orderFoodRequest.getQuantity();
            Food food = foodRepository.findById(orderFoodRequest.getFoodId()).orElseThrow(()->
                    new IllegalArgumentException("해당하는 음식이 존재하지 않습니다."));

            OrderFood orderFood = new OrderFood(order, food, quantity);
            orderFoods.add(orderFood);
        }

        orderFoodRepository.saveAll(orderFoods);

        return new OrderResponseDto(order, orderFoods);
    }

    private int calculateTotalPrice(List<OrderFoodDto> foodList) {

        int totalPrice = 0;
        for (OrderFoodDto orderFoodDto : foodList) {
            Food food = foodRepository.findById(orderFoodDto.getFoodId()).orElseThrow(()->
                    new IllegalArgumentException("해당하는 음식이 존재하지 않습니다."));
            totalPrice =  food.getFoodPrice() * orderFoodDto.getQuantity();
        }
        return totalPrice;
    }

    // CUSTOMER 별 주문 전체 조회
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrders(User user, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 사용자 주문 조회
        Page<Order> orderList = orderRepository.findAllByUser_UserId(user.getUserId(), pageable);
        System.out.println(user.getUserId());
        return orderList.map(order -> new OrderResponseDto(order, order.getOrderFoodList()));
    }

    // MASTER와 MANAGER 전체 주문 조회
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrdersByAdmin(User user, int page, int size, String sortBy, boolean isAsc) {

        UserRoleEnum role = user.getRole();

        if (role != UserRoleEnum.MASTER && role != UserRoleEnum.MANAGER) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> orderList = orderRepository.findAll(pageable);
        return orderList.map(order -> new OrderResponseDto(order, order.getOrderFoodList()));
    }

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


}
