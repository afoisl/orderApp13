package com.sparta.orderapp13.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponseDto {
    private String categoryName;
    private List<String> foodNames;
}
