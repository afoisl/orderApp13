package com.sparta.orderapp13.repository;

import com.sparta.orderapp13.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 삭제되지 않은 카테고리 목록만 조회
    @Query("SELECT c FROM Category c WHERE c.deletedAt IS NULL")
    List<Category> findAllActiveCategories();

    // 삭제되지 않은 특정 카테고리만 조회
    @Query("SELECT c FROM Category c WHERE c.categoryId = :categoryId AND c.deletedAt IS NULL")
    Optional<Category> findByIdAndNotDeleted(UUID categoryId);
}
