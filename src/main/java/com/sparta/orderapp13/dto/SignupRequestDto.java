
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
    private String username;

    @NotBlank
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank
    private String name;

    private boolean admin = false;
    private String adminToken = "";
}