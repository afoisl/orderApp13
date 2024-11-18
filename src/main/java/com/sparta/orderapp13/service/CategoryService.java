package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.CategoryRequestDto;
import com.sparta.orderapp13.dto.CategoryResponseDto;
import com.sparta.orderapp13.entity.Category;
import com.sparta.orderapp13.entity.Food;
import com.sparta.orderapp13.entity.User;
import com.sparta.orderapp13.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 새로운 카테고리를 생성하고 데이터베이스에 저장
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto, User user) {
        Category category = new Category();
        category.setCategoryName(requestDto.getCategoryName());

        // 생성자,수정자 정보를 설정 (updatedBy), null인 경우 기본값 설정 / 이후에는 인증된 사용자 정보에서 가져와서 저장하면 될 거 같음.
        category.setCreatedBy(user.getUserEmail());
        category.setUpdatedBy(user.getUserEmail());

        categoryRepository.save(category);
        return convertToResponseDto(category);
    }

    // 삭제되지 않은 모든 카테고리 목록을 조회
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAllActiveCategories();
        List<CategoryResponseDto> responseDtoList = new ArrayList<>();

        for (Category category : categories) {
            responseDtoList.add(convertToResponseDto(category));
        }

        return responseDtoList;
    }

    // ID로 삭제되지 않은 카테고리를 조회
    @Transactional
    public CategoryResponseDto getCategoryById(UUID categoryId) {
        Category category = categoryRepository.findByIdAndNotDeleted(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return convertToResponseDtoWithFoods(category);
    }

    // 카테고리 정보 수정
    public CategoryResponseDto updateCategory(UUID categoryId, CategoryRequestDto requestDto, User user) {
        Category category = categoryRepository.findByIdAndNotDeleted(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        category.setCategoryName(requestDto.getCategoryName());
        // createdBy가 null인 경우 기본값 설정 / 이후에는 인증된 사용자 정보에서 가져와서 저장하면 될 거 같음.
        category.setUpdatedBy(user.getUserEmail());
        category.setUpdatedAt(LocalDateTime.now());
        categoryRepository.save(category);
        return convertToResponseDto(category);
    }

    // 카테고리 소프트 삭제 - 삭제 날짜를 기록하고 실제 데이터는 삭제하지 않음
    public void deleteCategory(UUID categoryId, User user) {
        Category category = categoryRepository.findByIdAndNotDeleted(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        category.setUpdatedBy(user.getUserEmail());
        category.setDeletedAt(LocalDateTime.now()); // 소프트 삭제 날짜를 기록
        categoryRepository.save(category); // 업데이트된 엔티티 저장
    }

    // Category 엔티티를 CategoryResponseDto로 변환 (Food 리스트 제외)
    private CategoryResponseDto convertToResponseDto(Category category) {
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setCategoryName(category.getCategoryName());
        return responseDto;
    }

    // Category 엔티티를 CategoryResponseDto로 변환 (Food 리스트 포함)
    private CategoryResponseDto convertToResponseDtoWithFoods(Category category) {
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setCategoryName(category.getCategoryName());

        List<String> foodNames = new ArrayList<>();
        for (Food food : category.getFoods()) {
            foodNames.add(food.getFoodName());
        }
        responseDto.setFoodNames(foodNames); // 수집된 음식 이름 리스트 설정

        return responseDto;
    }


}
