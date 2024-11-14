package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
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

    @Enumerated
    @Column(name = "paymentMethod", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

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
}
