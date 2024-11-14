package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "p_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;

    // 기본 생성자
    public User() {
    }

    // 모든 필드를 포함한 생성자
    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter 메서드들
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRoleEnum getRole() {
        return role;
    }
}
