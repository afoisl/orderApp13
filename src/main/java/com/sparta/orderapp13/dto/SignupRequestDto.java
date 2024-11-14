package com.sparta.orderapp13.dto;

public class SignupRequestDto {

    private String userEmail;
    private String name;
    private String password;

    // 기본 생성자 및 getter/setter 추가
    public SignupRequestDto() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
