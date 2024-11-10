package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "foodId", nullable = false, updatable = false, unique = true)
    private UUID foodId;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @Column(name = "foodName", length = 255, nullable = false)
    private String foodName;

    @Column(name = "foodPrice", nullable = false)
    private BigInteger foodPrice;

    @Column(name = "foodImg", length = 1024)
    private String foodImg;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "createdBy", length = 100, nullable = false)
    private String createdBy;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "updatedBy", length = 100)
    private String updatedBy;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name = "deletedBy", length = 100)
    private String deletedBy;
}
