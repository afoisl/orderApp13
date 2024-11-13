package com.sparta.orderapp13.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.orderapp13.dto.StoreRequestDto;
import com.sparta.orderapp13.dto.StoreResponseDto;
import com.sparta.orderapp13.entity.Store;
import com.sparta.orderapp13.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {
    
    private final StoreRepository storeRepository;
    private final JPAQueryFactory queryFactory;

    public StoreResponseDto enroll(StoreRequestDto requestDto) {
        // requestDto 에 담긴 정보로 store 객체 생성
        Store store = new Store(requestDto);

        // DB 저장
        storeRepository.save(store);

        return new StoreResponseDto(store);
    }

    public List<StoreResponseDto> getAll(String categoryName) {
        return storeRepository.findAllByCategoryName(queryFactory, categoryName)
                .stream()
                .map(StoreResponseDto::new)
                .toList();
    }

    @Transactional
    public UUID update(UUID storeId, StoreRequestDto requestDto) {
        // 가게 조회
        Store store = storeRepository.findById(storeId).orElseThrow(()->
                        new NullPointerException("존재하지 않는 가게입니다."));

        // 수정 사항 업데이트
        store.update(requestDto);

        // 수정 정보 저장
        storeRepository.save(store);
        return storeId;
    }
}
