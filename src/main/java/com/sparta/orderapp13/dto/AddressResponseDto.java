package com.sparta.orderapp13.dto;

import com.sparta.orderapp13.entity.Address;
import lombok.Getter;

@Getter
public class AddressResponseDto {
    private Long addressId;
    private String region;
    private String city;
    private String detailedAddress;
    private Long postalCode;

    public AddressResponseDto(Address address) {
        this.addressId = address.getAddressId();
        this.region = address.getRegion();
        this.city = address.getCity();
        this.detailedAddress = address.getDetailedAddress();
        this.postalCode = address.getPostalCode();
    }
}
