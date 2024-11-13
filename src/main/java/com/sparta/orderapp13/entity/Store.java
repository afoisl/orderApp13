package com.sparta.orderapp13.entity;

import com.sparta.orderapp13.dto.StoreRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
@Table(name = "p_store")
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
    private boolean isDeleted = false;

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
