package com.sparta.orderapp13.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // Primary Key

    private String username; // 사용자 ID
    private String name; // 사용자 이름
    private String nickname; // 사용자 닉네임
    private String email; // 사용자 이메일
    private String password; // 사용자 비밀번호

    @Enumerated(EnumType.STRING)
    private Role role; // 역할 필드

    private boolean isPublic = true; // 정보 공개 여부, 기본값 TRUE

    // 시간 필드 추가
    private String createdBy;
    private String updatedBy;
    private String deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    public enum Role {
        CUSTOMER, OWNER, MANAGER, MASTER
    }
}
