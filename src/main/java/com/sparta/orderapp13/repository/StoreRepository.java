package com.sparta.orderapp13.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.orderapp13.entity.QCategory;
import com.sparta.orderapp13.entity.QStore;
import com.sparta.orderapp13.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    default List<Store> findAllByCategoryId(JPAQueryFactory queryFactory, UUID categoryId) {
        QStore store = QStore.store;
        QCategory category = QCategory.category;

        return queryFactory
                .selectFrom(store)
                .join(store.category, category)
                .where(category.categoryId.eq(categoryId),
                        store.deletedAt.isNull()) // DeletedAt 이 Null 인 가게만 조회
                .fetch();
    }

    Optional<Store> findByStoreIdAndDeletedAtIsNull(UUID storeId);

//    @Query("SELECT s FROM Store s WHERE s.storeName LIKE %:keyword% AND s.deletedAt IS NULL")
//    Page<Store> findByStoreName(String keyword, Pageable pageable);
}

