package com.sparta.orderapp13.repository;

import com.sparta.orderapp13.entity.Payment;
import com.sparta.orderapp13.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
