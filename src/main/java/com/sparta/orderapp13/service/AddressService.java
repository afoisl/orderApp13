package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.AddressRequestDto;
import com.sparta.orderapp13.dto.AddressResponseDto;
import com.sparta.orderapp13.entity.Address;
import com.sparta.orderapp13.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public AddressResponseDto createAddress(Long userId, AddressRequestDto requestDto, String createdBy) {
        // Address 엔티티를 생성하고 저장한 후, Response DTO로 변환하여 반환
        Address address = new Address(userId, requestDto.getRegion(), requestDto.getCity(),
                requestDto.getDetailedAddress(), requestDto.getPostalCode(), createdBy);
        addressRepository.save(address);
        return new AddressResponseDto(address);
    }

    public List<AddressResponseDto> getAddressesByUserId(Long userId) {
        // 특정 사용자 ID로 모든 주소를 조회하고, Response DTO 리스트로 반환
        List<Address> addresses = addressRepository.findAllByUserId(userId);
        return addresses.stream().map(AddressResponseDto::new).collect(Collectors.toList());
    }
}
