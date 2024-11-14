package com.sparta.orderapp13.repository;

import com.sparta.orderapp13.entity.Ai;
import com.sparta.orderapp13.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AiRepository extends JpaRepository<Ai, UUID> {

//     특정 Food 엔티티와 연결된 최신 AI 설명을 조회하는 메서드
//     AI 설명은 생성된 날짜를 기준으로 내림차순으로 정렬되며, 가장 최신의 설명을 반환

    Optional<Ai> findTopByFoodOrderByCreatedAtDesc(Food food);

    Optional<Ai> findTopByFoodNameOrderByCreatedAtDesc(String name);
}
