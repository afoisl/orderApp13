package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.StoreRequestDto;
import com.sparta.orderapp13.dto.StoreResponseDto;
import com.sparta.orderapp13.security.UserDetailsImpl;
import com.sparta.orderapp13.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    // 가게 등록
    @PostMapping("/stores")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public StoreResponseDto enroll(@RequestBody StoreRequestDto requestDto,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(requestDto.getCity());
        System.out.println(requestDto.getStoreName());
        System.out.println(requestDto.getCategoryId());
        System.out.println("user: "+userDetails.getUser());
        return storeService.enroll(requestDto, userDetails.getUser());
    }

    // 카테고리별 가게 조회
    @GetMapping("/stores/{categoryId}")
    public List<StoreResponseDto> getAll(@PathVariable UUID categoryId) {
        return storeService.getAll(categoryId);
    }

//    // 키워드로 가게 검색
//    @GetMapping("/stores/search")
//    public ResponseEntity<Page<StoreResponseDto>> search(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                        @RequestParam(value = "size", defaultValue = "10") int size,
//                                                        @RequestParam(value = "keyword") String keyword) {
//        return ResponseEntity.ok(storeService.search(page - 1, size, keyword));
//    }

    // 가게 상세 조회
    @GetMapping("/store/{storeId}")
    public StoreResponseDto get(@PathVariable UUID storeId) {
        return storeService.get(storeId);
    }

    // 가게 정보 수정
    @PutMapping("/stores/{storeId}")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public UUID update(@PathVariable UUID storeId, @RequestBody StoreRequestDto requestDto) {
        return storeService.update(storeId, requestDto);
    }

    // 가게 폐업 (상태 변경)
    @PatchMapping("/store/{storeId}")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'OWNER')")
    public UUID delete(@PathVariable UUID storeId) {
        return storeService.delete(storeId);
    }
    // ..
}
