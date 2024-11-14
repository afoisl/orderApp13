package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "p_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Email
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;

    // 기본 생성자
    public User() {
    }

    // 모든 필드를 포함한 생성자
    public User(String username, String password, String name, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.name = name;
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
