package com.sparta.orderapp13.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank
    private String userEmail;

    @NotBlank
    private String password;
}
