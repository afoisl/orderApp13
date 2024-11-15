package com.sparta.orderapp13.repository;

import com.sparta.orderapp13.entity.ReviewFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewFoodRepository extends JpaRepository<ReviewFood, UUID> {
}
