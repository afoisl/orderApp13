package com.sparta.orderapp13.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private UUID reviewId; // 리뷰 ID
    private int rating; // 평점
    private String reviewText; // 리뷰 내용
    private String replyText; // 리뷰 답글 내용
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
