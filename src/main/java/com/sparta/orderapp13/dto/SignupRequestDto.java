package com.sparta.orderapp13.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Email
    private String userEmail; // 유일한 식별자로 사용될 이메일

    @NotBlank
    @Size(min = 3, max = 20)
    private String name; // 사용자 이름

    @NotBlank
    @Size(min = 8, max = 30)
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
