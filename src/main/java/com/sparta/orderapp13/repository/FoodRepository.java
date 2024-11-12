package com.sparta.orderapp13.repository;

import com.sparta.orderapp13.entity.Category;
import com.sparta.orderapp13.entity.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {

    // 특정 카테고리에 속하고 삭제되지 않은 음식 목록 조회
    @Query("SELECT f FROM Food f WHERE f.category.categoryId = :categoryId AND f.deletedAt IS NULL")
    List<Food> findAllByCategoryId(UUID categoryId, Pageable pageable);

    // 삭제되지 않은 음식 아이디 조회
    @Query("SELECT f FROM Food f WHERE f.foodId = :foodId AND f.deletedAt IS NULL")
    Optional<Category> findByIdAndNotDeleted(UUID foodId);

    // 삭제되지 않은 음식 이름 검색
    @Query("SELECT f FROM Food f WHERE f.foodName LIKE %:keyword% AND f.deletedAt IS NULL")
    List<Food> findByName(String keyword, Pageable pageable);
}
