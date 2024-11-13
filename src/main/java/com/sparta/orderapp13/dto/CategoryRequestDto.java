package com.sparta.orderapp13.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    private String categoryName;
    private String createdBy; // 카테고리 생성 시 설정
    private String updatedBy; // 카테고리 수정 시 설정
}
