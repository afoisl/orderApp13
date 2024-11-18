package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.LoginRequestDto;
import com.sparta.orderapp13.dto.SignupRequestDto;
import com.sparta.orderapp13.entity.User;
import com.sparta.orderapp13.entity.UserRoleEnum;
import com.sparta.orderapp13.jwt.JwtUtil;
import com.sparta.orderapp13.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

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

        // 이메일 중복 확인
        if (userRepository.findByUserEmail(userEmail).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 기본 역할(CUSTOMER)로 사용자 생성
        UserRoleEnum role = UserRoleEnum.CUSTOMER;

        // 사용자 객체 생성 및 저장
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
        log.info("assignManager 호출 - userEmail: {}", userEmail);

        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getRole() == UserRoleEnum.CUSTOMER) {
            user.setRole(UserRoleEnum.MANAGER);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("이미 MANAGER 권한이 할당된 사용자입니다.");
        }
    }

    @Transactional
    public void assignOwner(String userEmail) {
        // 사용자 이메일로 검색
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 역할이 CUSTOMER인 경우에만 OWNER로 변경 가능
        if (user.getRole() == UserRoleEnum.CUSTOMER) {
            user.setRole(UserRoleEnum.OWNER);
            userRepository.save(user);
        } else if (user.getRole() == UserRoleEnum.OWNER) {
            throw new IllegalStateException("해당 사용자는 이미 OWNER 역할을 가지고 있습니다.");
        } else {
            throw new IllegalArgumentException("OWNER 역할은 CUSTOMER 사용자만 가질 수 있습니다.");
        }
    }

}
