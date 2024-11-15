package com.sparta.orderapp13.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FoodRequestDto {
    private UUID categoryId; // 카테고리 ID
    private UUID storeId; // 카테고리 ID
    private String name; // 음식 이름
    private Integer price; // 음식 가격
    private String foodImg; // 음식 사진 url
    private String description; // 음식 설명
    private String createdBy; // 카테고리 생성 시 설정
    private String updatedBy; // 카테고리 수정 시 설정
}
