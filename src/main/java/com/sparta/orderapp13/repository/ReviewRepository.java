package com.sparta.orderapp13.repository;

import com.sparta.orderapp13.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    // 특정 가게(storeId)에 대한 모든 리뷰를 조회하며, 삭제되지 않은(soft delete) 리뷰만 반환
    List<Review> findAllByStore_StoreIdAndDeletedAtIsNull(UUID storeId);

    // 특정 리뷰를 ID로 조회하며, 삭제되지 않은(soft delete) 리뷰만 반환
    Optional<Review> findByReviewIdAndDeletedAtIsNull(UUID reviewId);
}
