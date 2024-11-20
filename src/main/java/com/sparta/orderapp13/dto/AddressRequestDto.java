package com.sparta.orderapp13.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {
    private String region;
    private String city;
    private String detailedAddress;
    private Long postalCode; //
}
