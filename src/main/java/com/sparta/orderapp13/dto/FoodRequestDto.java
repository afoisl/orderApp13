package com.sparta.orderapp13.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FoodRequestDto {
    private UUID categoryId; // 카테고리 ID
    private String name; // 음식 이름
    private Integer price; // 음식 가격
    private String description; // 음식 설명
}
