package com.sparta.orderapp13.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class OrderFoodDetailDto {

    private String foodName;
    private String foodImg;
    private int price;
    private int quantity;

    public OrderFoodDetailDto(String foodName, String foodImg, int price, int quantity) {
        this.foodName = foodName;
        this.foodImg = foodImg;
        this.price = price;
        this.quantity = quantity;
    }
}
