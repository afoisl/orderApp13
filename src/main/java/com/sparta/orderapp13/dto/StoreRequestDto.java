package com.sparta.orderapp13.dto;

import com.sparta.orderapp13.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreRequestDto {

    private String storeName;
    private String storeNumber;
    private String region;
    private String city;
    private String detailAddress;
    private int postalCode;
    private UUID categoryId;
}
