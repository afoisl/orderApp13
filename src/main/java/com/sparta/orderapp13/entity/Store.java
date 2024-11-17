package com.sparta.orderapp13.entity;

import com.sparta.orderapp13.dto.StoreRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "p_store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "storeId", nullable = false, updatable = false, unique = true)
    private UUID storeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Size(max = 100)
    private String region;

    @Column(nullable = false)
    @Size(max = 100)
    private String city;

    @Column(nullable = false)
    @Size(max = 255)
    private String detailAddress;

    @Column(nullable = false)
    private int postalCode;

    @ManyToOne
    @JoinColumn(name = "category_id")
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
    private LocalDateTime createdAt = LocalDateTime.now();

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

    @OneToMany(mappedBy = "store")
    private List<Food> foods;

    @OneToMany(mappedBy = "store")
    private List<Food> reviews;

    public Store(StoreRequestDto requestDto, Category category, User user) {
        this.user = user;
        this.region = requestDto.getRegion();
        this.city = requestDto.getCity();
        this.detailAddress = requestDto.getDetailAddress();
        this.postalCode = requestDto.getPostalCode();
        this.category = category;
        this.storeName = requestDto.getStoreName();
        this.storeNumber = requestDto.getStoreNumber();
    }

    public void update(StoreRequestDto requestDto, Category category) {
        this.region = requestDto.getRegion();
        this.city = requestDto.getCity();
        this.detailAddress = requestDto.getDetailAddress();
        this.postalCode = requestDto.getPostalCode();
        this.category = category;
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
