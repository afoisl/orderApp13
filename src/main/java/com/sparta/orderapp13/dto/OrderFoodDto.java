package com.sparta.orderapp13.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class OrderFoodDto {

    private UUID foodId;
    private int quantity;
}
