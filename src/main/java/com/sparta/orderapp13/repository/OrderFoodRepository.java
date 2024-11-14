package com.sparta.orderapp13.repository;

import com.sparta.orderapp13.entity.OrderFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderFoodRepository extends JpaRepository<OrderFood, UUID> {
}
