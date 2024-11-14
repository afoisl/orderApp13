package com.sparta.orderapp13.dto;

import com.sparta.orderapp13.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreResponseDto {

    private UUID storeId;
    private String storeName;
    private String region;
    private String city;
    private String detailAddress;
    private int postalCode;

    public StoreResponseDto(Store store) {

        this.storeId = store.getStoreId();
        this.storeName = store.getStoreName();
        this.region = store.getRegion();
        this.city = store.getCity();
        this.detailAddress = store.getDetailAddress();
        this.postalCode = store.getPostalCode();
    }
}
