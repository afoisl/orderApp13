package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String detailedAddress;

    @Column(nullable = false)
    private Long postalCode;

    @Column(nullable = false)
    private String createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String updatedBy;

    private LocalDateTime updatedAt;

    private String deletedBy;

    private LocalDateTime deletedAt;

    public Address(Long userId, String region, String city, String detailedAddress, Long postalCode, String createdBy) {
        this.userId = userId;
        this.region = region;
        this.city = city;
        this.detailedAddress = detailedAddress;
        this.postalCode = postalCode;
        this.createdBy = createdBy;
    }

    // 업데이트 시 호출할 메서드 예시
    public void updateAddress(String region, String city, String detailedAddress, Long postalCode, String updatedBy) {
        this.region = region;
        this.city = city;
        this.detailedAddress = detailedAddress;
        this.postalCode = postalCode;
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }

    // 삭제 시 호출할 메서드 예시
    public void markAsDeleted(String deletedBy) {
        this.deletedBy = deletedBy;
        this.deletedAt = LocalDateTime.now();
    }
}
