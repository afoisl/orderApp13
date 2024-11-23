package com.sparta.orderapp13.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.orderapp13.dto.StoreRequestDto;
import com.sparta.orderapp13.dto.StoreResponseDto;
import com.sparta.orderapp13.entity.Category;
import com.sparta.orderapp13.entity.Store;
import com.sparta.orderapp13.entity.User;
import com.sparta.orderapp13.repository.CategoryRepository;
import com.sparta.orderapp13.repository.StoreRepository;
import com.sparta.orderapp13.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {
    
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public StoreResponseDto enroll(StoreRequestDto requestDto, User user) {

        System.out.println(requestDto.getCategoryId());
        // 카테고리 조회
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow((EntityNotFoundException::new));

        // 가게 등록한 User 저장
        User enrolledUser = userRepository.findById(user.getUserId())
                .orElseThrow((IllegalStateException::new));

        // requestDto 에 담긴 정보로 store 객체 생성
        Store store = new Store(requestDto, category, enrolledUser);

        // DB 저장
        storeRepository.save(store);

        return new StoreResponseDto(store);
    }

//    public Page<StoreResponseDto> search(int page, int size, String keyword) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Store> stores = storeRepository.findByStoreName(keyword, pageable);
//
//        return stores.map(StoreResponseDto::new);
//    }

    public List<StoreResponseDto> getAll(UUID categoryId) {
        List<Store> stores = storeRepository.findAllByCategoryId(queryFactory, categoryId);
        List<StoreResponseDto> storeList = new ArrayList<>();

        for (Store store : stores) {
            storeList.add(new StoreResponseDto(store));
        }

        return storeList;
    }

    public StoreResponseDto get(UUID storeId) {
        // 가게 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow((EntityNotFoundException::new));

        return new StoreResponseDto(store);
    }

    @Transactional
    public UUID update(UUID storeId, StoreRequestDto requestDto) {
        // 카테고리 조회
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow((EntityNotFoundException::new));

        // 가게 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow((EntityNotFoundException::new));

        // 수정 사항 업데이트
        store.update(requestDto, category);

        return storeId;
    }

    @Transactional
    public UUID delete(UUID storeId) {
        // 가게 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow((EntityNotFoundException::new));

        store.delete();
        return storeId;
    }

}
