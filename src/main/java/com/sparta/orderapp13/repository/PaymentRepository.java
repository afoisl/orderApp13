package com.sparta.orderapp13.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.orderapp13.entity.Payment;
import com.sparta.orderapp13.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

import static com.sparta.orderapp13.entity.QOrder.order;
import static com.sparta.orderapp13.entity.QPayment.payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    // 고객별 결제 내역 조회
    default List<Payment> findAllByOrderUser(User user, JPAQueryFactory queryFactory) {
        return queryFactory
                .selectFrom(payment)
                .join(payment.order, order)
                .where(order.user.eq(user))
                .fetch();
    }
}
