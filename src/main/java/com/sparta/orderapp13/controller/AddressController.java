package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.AddressRequestDto;
import com.sparta.orderapp13.dto.AddressResponseDto;
import com.sparta.orderapp13.security.UserDetailsImpl;
import com.sparta.orderapp13.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public AddressResponseDto createAddress(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody AddressRequestDto requestDto) {
        return addressService.createAddress(userDetails.getUser().getUserId(), requestDto, userDetails.getUsername());
    }

    @GetMapping
    public List<AddressResponseDto> getAddresses(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return addressService.getAddressesByUserId(userDetails.getUser().getUserId());
    }
}

