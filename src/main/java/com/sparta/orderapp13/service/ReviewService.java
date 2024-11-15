package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.ReviewReplyRequestDto;
import com.sparta.orderapp13.dto.ReviewRequestDto;
import com.sparta.orderapp13.dto.ReviewResponseDto;
import com.sparta.orderapp13.entity.Food;
import com.sparta.orderapp13.entity.Review;
import com.sparta.orderapp13.entity.ReviewFood;
import com.sparta.orderapp13.entity.Store;
import com.sparta.orderapp13.repository.FoodRepository;
import com.sparta.orderapp13.repository.ReviewFoodRepository;
import com.sparta.orderapp13.repository.ReviewRepository;
import com.sparta.orderapp13.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final ReviewFoodRepository reviewFoodRepository;
    private final FoodRepository foodRepository;

    // 리뷰 생성
    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto) {
        Store store = storeRepository.findByStoreIdAndDeletedAtIsNull(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

        Review review = new Review();
        review.setStore(store);
        review.setRating(requestDto.getRating());
        review.setReviewText(requestDto.getReviewText());
        // 생성자, 수정자 정보 설정. null일 경우 기본값 설정
        review.setCreatedBy(requestDto.getCreatedBy() != null ? requestDto.getCreatedBy() : "생성한 사람1");
        review.setUpdatedBy(requestDto.getUpdatedBy() != null ? requestDto.getUpdatedBy() : "수정한 사람1");

        reviewRepository.save(review);

        // 리뷰에 연결된 음식 정보 처리 및 저장
        List<ReviewFood> reviewFoods = new ArrayList<>();
        for (UUID foodId : requestDto.getFoodIds()) {
            Food food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new IllegalArgumentException("Food not found"));
            ReviewFood reviewFood = new ReviewFood();
            reviewFood.setReview(review);
            reviewFood.setFood(food);
            reviewFood.setCreatedBy(requestDto.getCreatedBy() != null ? requestDto.getCreatedBy() : "생성한 사람1");
            reviewFood.setUpdatedBy(requestDto.getUpdatedBy() != null ? requestDto.getUpdatedBy() : "수정한 사람1");
            reviewFoods.add(reviewFood);
        }

        reviewFoodRepository.saveAll(reviewFoods); // 연결 엔티티들 저장
        review.setReviewFoods(reviewFoods); // Review 엔티티에 음식 리스트 설정

        return convertToResponseDto(review);
    }

    // 리뷰에 답글 추가
    @Transactional
    public ReviewResponseDto addReplyToReview(UUID reviewId, ReviewReplyRequestDto replyRequestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.setReplyText(replyRequestDto.getReplyText()); // 답글 설정
        reviewRepository.save(review);

        return convertToResponseDto(review);
    }

    // 답글 수정
    @Transactional
    public ReviewResponseDto updateReply(UUID reviewId, ReviewReplyRequestDto replyUpdateRequestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.setReplyText(replyUpdateRequestDto.getReplyText()); // 답글 수정
        reviewRepository.save(review);

        return convertToResponseDto(review);
    }


    // 특정 가게의 모든 리뷰 조회
    public List<ReviewResponseDto> getReviewsByStoreId(UUID storeId) {
        List<Review> reviews = reviewRepository.findAllByStore_StoreIdAndDeletedAtIsNull(storeId);

        List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponseDtos.add(convertToResponseDto(review));
        }

        return reviewResponseDtos;
    }


    // 리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(UUID reviewId, ReviewRequestDto requestDto) {
        Review review = reviewRepository.findByReviewIdAndDeletedAtIsNull(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.setRating(requestDto.getRating());
        review.setReviewText(requestDto.getReviewText());
        review.setUpdatedBy(requestDto.getUpdatedBy());
        review.setUpdatedAt(LocalDateTime.now());

        return convertToResponseDto(review);
    }

    // 리뷰 삭제 (소프트 삭제)
    @Transactional
    public void deleteReview(UUID reviewId, String deletedBy) {
        Review review = reviewRepository.findByReviewIdAndDeletedAtIsNull(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.setDeletedAt(LocalDateTime.now());
        review.setDeletedBy(deletedBy);
    }

    // Review 엔티티를 ReviewResponseDto로 변환
    private ReviewResponseDto convertToResponseDto(Review review) {
        ReviewResponseDto responseDto = new ReviewResponseDto();
        responseDto.setReviewId(review.getReviewId());

        // 중복된 음식 이름을 제거하고 리스트로 설정
        List<String> foodNames = review.getReviewFoods().stream()
                .map(reviewFood -> reviewFood.getFood().getFoodName())
                .distinct() // 중복된 음식 이름 제거
                .collect(Collectors.toList());
        responseDto.setFoodNames(foodNames); // 음식 이름 리스트 설정

        responseDto.setRating(review.getRating());
        responseDto.setReviewText(review.getReviewText());
        responseDto.setReplyText(review.getReplyText());
        responseDto.setCreatedAt(review.getCreatedAt());
        responseDto.setCreatedBy(review.getCreatedBy());
        responseDto.setUpdatedAt(review.getUpdatedAt());
        responseDto.setUpdatedBy(review.getUpdatedBy());
        return responseDto;
    }
}
