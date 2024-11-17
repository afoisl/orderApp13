package com.sparta.orderapp13.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.orderapp13.dto.PaymentRequestDto;
import com.sparta.orderapp13.dto.PaymentResponseDto;
import com.sparta.orderapp13.entity.Order;
import com.sparta.orderapp13.entity.Payment;
import com.sparta.orderapp13.entity.User;
import com.sparta.orderapp13.entity.UserRoleEnum;
import com.sparta.orderapp13.repository.OrderRepository;
import com.sparta.orderapp13.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final JPAQueryFactory queryFactory;

    public PaymentResponseDto create(PaymentRequestDto requestDto) {
        // Order 조회
        Order order = orderRepository.findById(requestDto.getOrderId()).orElseThrow(()->
                new IllegalArgumentException("해당하는 주문이 존재하지 않습니다."));

        int paymentAmount = requestDto.getPaymentAmount();
        String paymentMethod = requestDto.getPaymentMethod();
        String paymentStatus = requestDto.getPaymentStatus();

        // Payment 객체 생성
        Payment payment = paymentRepository.save(new Payment(order, paymentAmount, paymentMethod, paymentStatus));

        return new PaymentResponseDto(payment);
    }

    public List<PaymentResponseDto> getAll(User user) {
        // 권한별로 조회
        UserRoleEnum role = user.getRole();

        List<Payment> paymentList;

        if (role == UserRoleEnum.CUSTOMER) {
            paymentList = paymentRepository.findAllByOrderUser(user, queryFactory);
        } else {
            paymentList = paymentRepository.findAll();
        }

        List<PaymentResponseDto> paymentDtoList = new ArrayList<>();
        for (Payment payment : paymentList) {
            paymentDtoList.add(new PaymentResponseDto(payment));
        }

        return paymentDtoList;
    }

    public PaymentResponseDto get(UUID paymentId) {
        // Payment 조회
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->
                new IllegalArgumentException("결제 내역이 존재하지 않습니다."));

        return new PaymentResponseDto(payment);
    }

    public UUID cancel(UUID paymentId) {
        // Payment 조회
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->
                new IllegalArgumentException("결제 내역이 존재하지 않습니다."));

        // PaymentStatue 바꾸기
        payment.cancel();

        return payment.getPaymentId();
    }
}
