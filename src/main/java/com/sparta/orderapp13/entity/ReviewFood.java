package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_review_food")
public class ReviewFood {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reviewFoodId", nullable = false, updatable = false, unique = true)
    private UUID reviewFoodId;

    @ManyToOne
    @JoinColumn(name = "reviewId", nullable = false)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "foodId", nullable = false)
    private Food food;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "createdBy", length = 100, nullable = false)
    private String createdBy;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "updatedBy", length = 100)
    private String updatedBy;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name = "deletedBy", length = 100)
    private String deletedBy;

    public ReviewFood(Review review, Food food) {
        this.review = review;
        this.food = food;
    }
}
