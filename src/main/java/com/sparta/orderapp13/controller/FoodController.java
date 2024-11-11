package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.FoodRequestDto;
import com.sparta.orderapp13.dto.FoodResponseDto;
import com.sparta.orderapp13.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    // 음식 등록
    @PostMapping
    public ResponseEntity<FoodResponseDto> createFood(@RequestBody FoodRequestDto requestDto) {
        FoodResponseDto responseDto = foodService.createFood(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 카테고리별 음식 목록 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<FoodResponseDto>> getFoodsByCategory(
            @PathVariable UUID categoryId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {
        List<FoodResponseDto> foods = foodService.getFoodsByCategory(categoryId, page, size, sortBy);
        return ResponseEntity.ok(foods);
    }

    // 음식 검색
    @GetMapping
    public ResponseEntity<List<FoodResponseDto>> searchFoods(
            @RequestParam String search,
            @RequestParam int page,
            @RequestParam int size) {
        List<FoodResponseDto> foods = foodService.searchFoods(search, page, size);
        return ResponseEntity.ok(foods);
    }

    // 음식 수정
    @PutMapping("/{foodId}")
    public ResponseEntity<FoodResponseDto> updateFood(
            @PathVariable UUID foodId,
            @RequestBody FoodRequestDto requestDto) {
        FoodResponseDto responseDto = foodService.updateFood(foodId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 음식 소프트 삭제
    @DeleteMapping("/{foodId}")
    public ResponseEntity<Void> deleteFood(@PathVariable UUID foodId) {
        foodService.deleteFood(foodId);
        return ResponseEntity.noContent().build();
    }

    // 음식 상세 조회
    @GetMapping("/{foodId}")
    public ResponseEntity<FoodResponseDto> getFoodById(@PathVariable UUID foodId) {
        FoodResponseDto responseDto = foodService.getFoodById(foodId);
        return ResponseEntity.ok(responseDto);
    }
}
