package com.sparta.orderapp13.entity;

import com.sparta.orderapp13.dto.StoreRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "p_store")
public class Store {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID storeId;

    @Column(name = "region", nullable = false)
    @Max(100)
    private String region;

    @Column(name = "city", nullable = false)
    @Max(100)
    private String city;

    @Column(name = "detailAddress", nullable = false)
    @Max(255)
    private String detailAddress;

    @Column(name = "postalCode", nullable = false)
    private int postalCode;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "storeName", nullable = false)
    private String storeName;

    @Column(name = "storeNumber", nullable = false)
    private String storeNumber;

    @Column(name = "isOpen")
    private boolean isOpen = false;

    @Column(name = "isDeleted")
    private boolean isDeleted = false;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

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

    public Store(StoreRequestDto requestDto) {
        this.region = requestDto.getRegion();
        this.city = requestDto.getCity();
        this.detailAddress = requestDto.getDetailAddress();
        this.postalCode = requestDto.getPostalCode();
        this.category = requestDto.getCategory();
        this.storeName = requestDto.getStoreName();
        this.storeNumber = requestDto.getStoreNumber();
    }

    public void update(StoreRequestDto requestDto) {
        this.region = requestDto.getRegion();
        this.city = requestDto.getCity();
        this.detailAddress = requestDto.getDetailAddress();
        this.postalCode = requestDto.getPostalCode();
        this.category = requestDto.getCategory();
        this.storeName = requestDto.getStoreName();
        this.storeNumber = requestDto.getStoreNumber();
    }

    public void delete() {
        if (!this.isDeleted) {
            this.isDeleted = true;
            this.deletedAt = LocalDateTime.now(); // 폐업일 설정
        } else {
            throw new IllegalStateException("이미 폐업된 가게입니다.");
        }
    }
}
