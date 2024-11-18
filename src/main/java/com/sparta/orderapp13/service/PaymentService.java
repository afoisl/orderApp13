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
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow((IllegalArgumentException::new));

        int paymentAmount = requestDto.getPaymentAmount();
        String paymentMethod = requestDto.getPaymentMethod();
        String paymentStatus = requestDto.getPaymentStatus();

        // Payment 객체 생성
        Payment payment = paymentRepository.save(
                new Payment(order, paymentAmount, paymentMethod, paymentStatus));

        return new PaymentResponseDto(payment);
    }

    public List<PaymentResponseDto> getAll(User user) {
        List<Payment> paymentList;

        paymentList = paymentRepository.findAllByOrderUser(user, queryFactory);

        List<PaymentResponseDto> paymentDtoList = new ArrayList<>();
        for (Payment payment : paymentList) {
            paymentDtoList.add(new PaymentResponseDto(payment));
        }

        return paymentDtoList;
    }

    public List<PaymentResponseDto> getAllByAdmin(User user) {
            List<Payment> paymentList;

            paymentList = paymentRepository.findAll();

            List<PaymentResponseDto> paymentDtoList = new ArrayList<>();
            for (Payment payment : paymentList) {
                paymentDtoList.add(new PaymentResponseDto(payment));
            }

            return paymentDtoList;
    }

    public PaymentResponseDto get(UUID paymentId) {
        // Payment 조회
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow((IllegalArgumentException::new));

        return new PaymentResponseDto(payment);
    }

    public UUID cancel(UUID paymentId) {
        // Payment 조회
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow((IllegalArgumentException::new));

        // PaymentStatue 바꾸기
        payment.cancel();

        return payment.getPaymentId();
    }
}
