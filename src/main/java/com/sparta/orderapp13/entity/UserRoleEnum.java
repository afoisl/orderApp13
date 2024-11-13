package com.sparta.orderapp13.entity;

public enum UserRoleEnum {
    USER("ROLE_USER"),  // 일반 사용자 권한
    ADMIN("ROLE_ADMIN");  // 관리자 권한

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
