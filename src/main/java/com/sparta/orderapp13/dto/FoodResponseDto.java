package com.sparta.orderapp13.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FoodResponseDto {
    private UUID id; // 음식 ID UUID 주의
    private String name; // 음식 이름
    private Integer price; // 음식 가격
    private String foodImg; // 음식 사진
    private String description; // 음식 설명
}
