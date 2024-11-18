package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.CategoryRequestDto;
import com.sparta.orderapp13.dto.CategoryResponseDto;
import com.sparta.orderapp13.entity.Category;
import com.sparta.orderapp13.security.UserDetailsImpl;
import com.sparta.orderapp13.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 생성
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CategoryResponseDto responseDto = categoryService.createCategory(requestDto, userDetails.getUser());
        return ResponseEntity.ok(responseDto); // 생성된 카테고리 정보를 응답으로 반환
    }

    // 모든 카테고리 목록 조회
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // 특정 ID 카테고리 조회 (연관된 음식 보기 위해서 사용)
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable UUID categoryId) {
        CategoryResponseDto responseDto = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(responseDto);
    }


    // 특정 ID 카테고리 정보 수정
    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID categoryId,
                                                              @RequestBody CategoryRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CategoryResponseDto responseDto = categoryService.updateCategory(categoryId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    // 특정 ID에 해당하는 카테고리를 소프트 삭제
    // deleted_by에 삭제한 사람 들어가게 해야 함
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        categoryService.deleteCategory(categoryId, userDetails.getUser());
        return ResponseEntity.noContent().build(); // 성공적으로 삭제 시 빈 응답 반환
    }

}
