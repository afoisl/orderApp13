package com.sparta.orderapp13.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId", nullable = false, updatable = false, unique = true)
    private UUID categoryId;

    @Column(name = "categoryName", length = 100, nullable = false)
    private String categoryName;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Default creation time

//  user 완성되면 nullable = false 추가하고 자동으로 추가 되도록 코드 수정해야됨
    @Column(name = "createdBy", length = 100)
    private String createdBy;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now(); // Default creation time

    @Column(name = "updatedBy", length = 100)
    private String updatedBy;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name = "deletedBy", length = 100)
    private String deletedBy;

    @OneToMany(mappedBy = "category")
    private List<Food> foods;
}
