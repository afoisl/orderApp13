package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "p_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // PK로 설정된 userId

    @Email
    @Column(nullable = false, unique = true)
    private String userEmail; // 유일한 식별자로 사용될 이메일

    @Column(nullable = false)
    private String name; // 사용자 이름

    @Column(nullable = false)
    private String password; // 사용자 비밀번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role; // 사용자 역할 (CUSTOMER, OWNER, MANAGER, MASTER)

    @Column(nullable = false)
    private boolean isPublic = true; // 개인정보 공개 여부, 기본값 true

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "createdBy", length = 100, nullable = false)
    private String createdBy;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now(); // Default creation time

    @Column(name = "updatedBy", length = 100)
    private String updatedBy;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name = "deletedBy", length = 100)
    private String deletedBy;

    // 기본 생성자
    public User() {
    }

    // 모든 필드를 포함한 생성자
    public User(String userEmail, String name, String password, UserRoleEnum role) {
        this.userEmail = userEmail;
        this.name = name;
        this.password = password;
        this.role = role;
        this.createdBy = userEmail;
    }

}
