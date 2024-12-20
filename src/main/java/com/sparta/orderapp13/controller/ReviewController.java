package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.ReviewReplyRequestDto;
import com.sparta.orderapp13.dto.ReviewRequestDto;
import com.sparta.orderapp13.dto.ReviewResponseDto;
import com.sparta.orderapp13.security.UserDetailsImpl;
import com.sparta.orderapp13.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ReviewResponseDto responseDto = reviewService.createReview(requestDto, userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }


    // 특정 가게의 모든 리뷰 조회
    @GetMapping("/{storeId}/reviewInfo")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByStoreId(@PathVariable UUID storeId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByStoreId(storeId);
        return ResponseEntity.ok(reviews);
    }

    // 리뷰 수정
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'CUSTOMER')")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable UUID reviewId,
            @RequestBody ReviewRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'CUSTOMER')")
    public ResponseEntity<Void> deleteReview(
            @PathVariable UUID reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.deleteReview(reviewId, userDetails.getUser());
        return noContent().build();
    }

    // 리뷰에 답글 달기
    @PostMapping("/{reviewId}/reply")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public ResponseEntity<ReviewResponseDto> addReplyToReview(
            @PathVariable UUID reviewId,
            @RequestBody ReviewReplyRequestDto replyRequestDto) {
        ReviewResponseDto responseDto = reviewService.addReplyToReview(reviewId, replyRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 리뷰 답글 수정
    @PutMapping("/{reviewId}/reply")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public ResponseEntity<ReviewResponseDto> updateReply(
            @PathVariable UUID reviewId,
            @RequestBody ReviewReplyRequestDto replyUpdateRequestDto) {
        ReviewResponseDto responseDto = reviewService.updateReply(reviewId, replyUpdateRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 리뷰 답글 삭제
    @DeleteMapping("/{reviewId}/replyDelete")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public ResponseEntity<Void> deleteReply(
            @PathVariable UUID reviewId
    ) {
        reviewService.deleteReply(reviewId);
        return ResponseEntity.noContent().build();
    }
}
