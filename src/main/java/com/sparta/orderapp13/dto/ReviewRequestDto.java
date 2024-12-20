package com.sparta.orderapp13.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private UUID storeId; // 가게 ID
    private List<UUID> foodIds;
    private int rating; // 평점
    private String reviewText; // 리뷰 내용
}
