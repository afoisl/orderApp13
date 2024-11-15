package com.sparta.orderapp13.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private UUID storeId; // 가게 ID
    private int rating; // 평점
    private String reviewText; // 리뷰 내용
    private String createdBy;
    private String updatedBy;
}
