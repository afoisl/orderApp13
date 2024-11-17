package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "p_payment")
public class Payment {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "paymentId")
    private UUID paymentId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "paymentAmount", nullable = false)
    private int paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentMethod", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "createdBy", length = 100)
    private String createdBy;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "updatedBy", length = 100)
    private String updatedBy;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name = "deletedBy", length = 100)
    private String deletedBy;

    public Payment(Order order, int paymentAmount, String paymentMethod, String paymentStatus) {
        this.order = order;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = PaymentMethod.valueOf(paymentMethod);
        this.paymentStatus = PaymentStatus.valueOf(paymentStatus);
    }

    public void cancel() {
        this.paymentStatus = PaymentStatus.PAYMENT_CANCELED;
        this.deletedAt = LocalDateTime.now();
    }

    // Order 에서 User 가져오기
    public User getOrderUser() {
        return order.getUser();
    }
}

