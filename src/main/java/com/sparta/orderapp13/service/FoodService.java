package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.FoodRequestDto;
import com.sparta.orderapp13.dto.FoodResponseDto;
import com.sparta.orderapp13.entity.Category;
import com.sparta.orderapp13.entity.Food;
import com.sparta.orderapp13.repository.CategoryRepository;
import com.sparta.orderapp13.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    // 음식 등록
    public FoodResponseDto createFood(FoodRequestDto requestDto) {
        Category category = categoryRepository.findByIdAndNotDeleted(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Food food = new Food();
        food.setCategory(category);
        food.setFoodName(requestDto.getName());
        food.setFoodPrice(requestDto.getPrice());
        food.setFoodImg(requestDto.getFoodImg());
        food.setDescription(requestDto.getDescription());

        // 생성자,수정자 정보를 설정 (updatedBy), null인 경우 기본값 설정 / 이후에는 인증된 사용자 정보에서 가져와서 저장하면 될 거 같음.
        food.setCreatedBy(requestDto.getCreatedBy() != null ? requestDto.getCreatedBy() : "생성한 사람1");
        food.setUpdatedBy(requestDto.getUpdatedBy() != null ? requestDto.getUpdatedBy() : "수정한 사람1");

        foodRepository.save(food);
        return convertToResponseDto(food);
    }

    // 카테고리별 음식 목록 조회 (삭제되지 않은 항목만)
    public List<FoodResponseDto> getFoodsByCategory(UUID categoryId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by(sortBy));
        List<Food> foods = foodRepository.findAllByCategoryId(categoryId, pageable);
        List<FoodResponseDto> responseDtoList = new ArrayList<>();

        for (Food food : foods) {
            responseDtoList.add(convertToResponseDto(food));
        }

        return responseDtoList;
    }

    // 음식 검색 (삭제되지 않은 항목만)
    public List<FoodResponseDto> searchFoods(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Food> foods = foodRepository.findByName(keyword, pageable);
        List<FoodResponseDto> responseDtoList = new ArrayList<>();

        for (Food food : foods) {
            responseDtoList.add(convertToResponseDto(food));
        }

        return responseDtoList;
    }


    // 음식 수정
    public FoodResponseDto updateFood(UUID foodId, FoodRequestDto requestDto) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

        food.setFoodName(requestDto.getName());
        food.setFoodPrice(requestDto.getPrice());
        food.setDescription(requestDto.getDescription());
        food.setFoodImg(requestDto.getFoodImg());
        food.setUpdatedAt(LocalDateTime.now());

        food.setUpdatedBy(requestDto.getUpdatedBy() != null ? requestDto.getUpdatedBy() : "수정한 사람1");
        food.setUpdatedAt(LocalDateTime.now());

        foodRepository.save(food);
        return convertToResponseDto(food);
    }

    // 소프트 삭제 (deletedAt에 삭제 시간 기록)
    public void deleteFood(UUID foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

        food.setDeletedAt(LocalDateTime.now()); // 소프트 삭제 시간 기록
        foodRepository.save(food); // 실제 삭제하지 않고, deletedAt만 갱신하여 저장
    }

    // 삭제되지 않은 음식 상세 조회
    public FoodResponseDto getFoodById(UUID foodId) {
        Food food = foodRepository.findById(foodId)
                .filter(f -> f.getDeletedAt() == null)
                .orElseThrow(() -> new IllegalArgumentException("Food not found or has been deleted"));
        return convertToResponseDto(food);
    }

    // Food 엔티티를 FoodResponseDto로 변환
    private FoodResponseDto convertToResponseDto(Food food) {
        FoodResponseDto responseDto = new FoodResponseDto();
        responseDto.setId(food.getFoodId());
        responseDto.setName(food.getFoodName());
        responseDto.setFoodImg(food.getFoodImg());
        responseDto.setPrice(food.getFoodPrice());
        responseDto.setDescription(food.getDescription());
        return responseDto;
    }
}
