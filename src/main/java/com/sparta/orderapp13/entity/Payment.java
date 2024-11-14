package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID paymentId;

    @OneToOne
    @JoinColumn
    private Order order;

    @Column(nullable = false)
    private int paymentAmount;

    @Enumerated
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated
    @Column(nullable = false)
    private PaymentStatus Status;

    @Column
    private String createdBy;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private String updatedBy;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private String deletedBy;
}
