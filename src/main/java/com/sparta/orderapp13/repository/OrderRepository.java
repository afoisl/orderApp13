package com.sparta.orderapp13.repository;


import com.sparta.orderapp13.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAllByUser_UserIdAndDeletedAtIsNull(Long userId, Pageable pageable);
}
