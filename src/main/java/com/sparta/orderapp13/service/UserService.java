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

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 중복된 회원 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        UserRoleEnum role = requestDto.isAdmin() ? UserRoleEnum.ADMIN : UserRoleEnum.USER;

        User user = new User(username, encodedPassword, role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        // 토큰을 응답 헤더에 추가
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.BEARER_PREFIX + token);
    }
}
