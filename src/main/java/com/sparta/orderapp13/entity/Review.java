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
@Table(name = "p_review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reviewId", nullable = false, updatable = false, unique = true)
    private UUID reviewId;

    @ManyToOne
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "reviewText", length = 255)
    private String reviewText;

    @Column(name = "replyText", length = 255)
    private String replyText;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewFood> reviewFoods;

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
}
