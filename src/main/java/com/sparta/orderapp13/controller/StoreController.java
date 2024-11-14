package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.StoreRequestDto;
import com.sparta.orderapp13.dto.StoreResponseDto;
import com.sparta.orderapp13.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public StoreResponseDto enroll(@RequestBody StoreRequestDto requestDto) {
        System.out.println(requestDto.getCity());
        System.out.println(requestDto.getStoreName());
        System.out.println(requestDto.getCategoryId());
        return storeService.enroll(requestDto);
    }

    @GetMapping("/stores")
    public List<StoreResponseDto> getAll(@RequestParam String category) {
        return storeService.getAll(category);
    }

    @GetMapping("/store/{storeId}")
    public StoreResponseDto getStore(@PathVariable UUID storeId) {
        return storeService.getStore(storeId);
    }

    @PutMapping("/stores/{storeId}")
    public UUID update(@PathVariable UUID storeId, @RequestBody StoreRequestDto requestDto) {
        return storeService.update(storeId, requestDto);
    }

    @PatchMapping("/store/{storeId}")
    public UUID delete(@PathVariable UUID storeId) {
        return storeService.delete(storeId);
    }
}
