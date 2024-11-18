package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.FoodRequestDto;
import com.sparta.orderapp13.dto.FoodResponseDto;
import com.sparta.orderapp13.entity.*;
import com.sparta.orderapp13.repository.AiRepository;
import com.sparta.orderapp13.repository.CategoryRepository;
import com.sparta.orderapp13.repository.FoodRepository;
import com.sparta.orderapp13.repository.StoreRepository;
import com.sparta.orderapp13.util.SizeValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.sparta.orderapp13.entity.QStore.store;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final AiRepository aiRepository;
    private final GeminiService geminiService;

    // 음식 등록 새로운 음식을 등록하고, 필요시 AI를 통해 설명을 생성

    @Transactional
    public FoodResponseDto createFood(FoodRequestDto requestDto, User user) {
        Category category = categoryRepository.findByIdAndNotDeleted(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        Store store = storeRepository.findByStoreIdAndDeletedAtIsNull(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

        // 가게 소유주 검증
        if (user.getRole().equals(UserRoleEnum.OWNER) && !store.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalStateException("본인 가게의 음식만 생성 할 수 있습니다.");
        }

        Food food = new Food();
        food.setCategory(category); // 카테고리 설정
        food.setStore(store);
        food.setFoodName(requestDto.getName());
        food.setFoodPrice(requestDto.getPrice());
        food.setFoodImg(requestDto.getFoodImg());

        // 생성자, 수정자 정보 설정. null일 경우 기본값 설정
        food.setCreatedBy(user.getUserEmail());
        food.setUpdatedBy(user.getUserEmail());

        // Food 객체를 저장하여 기본 키 ID를 할당
        foodRepository.save(food);

        // AI 기록 조회: 동일한 음식 이름으로 가장 최근에 생성된 설명이 있는지 확인
//        Optional<Ai> latestAi = aiRepository.findTopByFoodOrderByCreatedAtDesc(food);
        Optional<Ai> latestAi = aiRepository.findTopByFoodNameOrderByCreatedAtDesc(requestDto.getName());
        if (latestAi.isPresent()) {
            // 최신 설명이 있으면 이를 음식 설명에 설정
            food.setDescription(latestAi.get().getResponseText());
        } else {
            // AI로부터 설명을 요청할 필요가 있을 경우 설명 요청 prompt 생성
            String prompt = requestDto.getName() + "을 설명해줘 30자 이내로";
            String aiDescription = geminiService.getDescriptionFromAI(prompt);

            // 응답 텍스트에서 불필요한 이모지 및 줄바꿈을 제거한 설명 텍스트
            String cleanDescription = aiDescription.replaceAll("[\\p{So}\\n]", "");

            // 새롭게 생성한 AI 기록을 DB에 저장
            Ai aiRecord = new Ai();
            aiRecord.setFood(food);
            aiRecord.setFoodName(food.getFoodName());
            aiRecord.setRequestText(prompt);
            aiRecord.setResponseText(cleanDescription); // 가공된 설명 텍스트 설정
            aiRecord.setCreatedBy("AI 시스템"); // 생성자 기본값 설정
            aiRecord.setUpdatedBy("AI 시스템"); // 수정자 기본값 설정
            aiRepository.save(aiRecord);

            // Food 객체에 생성한 설명을 설정
            food.setDescription(cleanDescription);
        }

        return convertToResponseDto(food); // 음식 정보를 응답 DTO로 변환하여 반환
    }

    // 카테고리별 음식 목록 조회 (삭제되지 않은 항목만)
    public List<FoodResponseDto> getFoodsByCategory(UUID categoryId, int page, int size, String sortBy) {
        // 사이즈 검증 및 허용된 값으로 제한
        size = SizeValidator.validateSize(size);

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
        // 사이즈 검증 및 허용된 값으로 제한
        size = SizeValidator.validateSize(size);

        Pageable pageable = PageRequest.of(page, size);
        List<Food> foods = foodRepository.findByName(keyword, pageable);
        List<FoodResponseDto> responseDtoList = new ArrayList<>();

        for (Food food : foods) {
            responseDtoList.add(convertToResponseDto(food));
        }

        return responseDtoList;
    }


    // 음식 수정
    public FoodResponseDto updateFood(UUID foodId, FoodRequestDto requestDto, User user) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

        Store store = storeRepository.findByStoreIdAndDeletedAtIsNull(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

        // 가게 소유주 검증
        if (user.getRole().equals(UserRoleEnum.OWNER)) {
            if(!store.getUser().getUserId().equals(user.getUserId())){
                throw new IllegalStateException("본인 가게의 음식만 수정 할 수 있습니다.");
            }
        }
        

        food.setFoodName(requestDto.getName());
        food.setFoodPrice(requestDto.getPrice());
        food.setDescription(requestDto.getDescription());
        food.setFoodImg(requestDto.getFoodImg());

        food.setUpdatedBy(user.getUserEmail());
        food.setUpdatedAt(LocalDateTime.now());

        foodRepository.save(food);
        return convertToResponseDto(food);
    }

    // 소프트 삭제 (deletedAt에 삭제 시간 기록)
    public void deleteFood(UUID foodId, UUID storeId, User user) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));
        Store store = storeRepository.findByStoreIdAndDeletedAtIsNull(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

        // 가게 소유주 검증
        if (user.getRole().equals(UserRoleEnum.OWNER)) {
            if(!store.getUser().getUserId().equals(user.getUserId())){
                throw new IllegalStateException("본인 가게의 음식만 삭제 할 수 있습니다.");
            }
        }
        food.setDeletedBy(user.getUserEmail());
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
