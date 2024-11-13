package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID storeId;

    @Column(nullable = false)
    @Max(100)
    private String region;

    @Column(nullable = false)
    @Max(100)
    private String city;

    @Column(nullable = false)
    @Max(255)
    private String detailAddress;

    @Column(nullable = false)
    private int postalCode;

    @ManyToOne
    @JoinColumn
    private Category category;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String storeNumber;

    @Column
    private boolean isOpen = false;

    @Column
    private LocalDateTime createdAt;

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
