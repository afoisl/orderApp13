package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.LoginRequestDto;
import com.sparta.orderapp13.dto.SignupRequestDto;
import com.sparta.orderapp13.entity.User;
import com.sparta.orderapp13.entity.UserRoleEnum;
import com.sparta.orderapp13.jwt.JwtUtil;
import com.sparta.orderapp13.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입 메서드
    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String userEmail = requestDto.getUserEmail();
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 중복된 이메일 확인 (이메일은 중복 안 됨)
        Optional<User> foundByEmail = userRepository.findByUserEmail(userEmail);
        if (foundByEmail.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 기본 역할(CUSTOMER)로 사용자 생성
        UserRoleEnum role = UserRoleEnum.CUSTOMER;

        // 이름 중복은 허용하고 이메일만 중복 불가
        User user = new User(userEmail, requestDto.getName(), encodedPassword, role);
        userRepository.save(user);
    }

    // 로그인 메서드 (토큰 생성 및 반환)
    @Transactional(readOnly = true)
    public String login(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByUserEmail(requestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtUtil.createToken(user.getUserEmail(), user.getRole());

        // 응답 헤더에 JWT 토큰 설정
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.BEARER_PREFIX + token);

        // 토큰 반환
        return token;
    }

    // CUSTOMER 사용자에게 MANAGER 역할 할당 메서드
    @Transactional
    public void assignManager(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getRole() == UserRoleEnum.CUSTOMER) {
            user.setRole(UserRoleEnum.MANAGER);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("이미 MANAGER 권한이 할당된 사용자입니다.");
        }
    }
}
