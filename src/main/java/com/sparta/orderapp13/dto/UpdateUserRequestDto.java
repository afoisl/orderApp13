// UpdateUserRequestDto.java
package com.sparta.orderapp13.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
