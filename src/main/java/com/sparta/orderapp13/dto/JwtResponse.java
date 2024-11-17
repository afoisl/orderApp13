package com.sparta.orderapp13.dto;

public class JwtResponse {

    private String token;

    // 생성자
    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter , Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
