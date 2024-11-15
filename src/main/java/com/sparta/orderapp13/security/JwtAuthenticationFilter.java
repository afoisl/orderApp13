package com.sparta.orderapp13.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.orderapp13.dto.LoginRequestDto;
import com.sparta.orderapp13.entity.UserRoleEnum;
import com.sparta.orderapp13.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super.setAuthenticationManager(authenticationManager); // AuthenticationManager 설정
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login"); // 로그인 요청 경로 설정
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("로그인 시도");

        try {
            // 요청 데이터를 DTO로 변환
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            // 사용자 인증 토큰 생성
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserEmail(), // 이메일 사용
                            requestDto.getPassword()
                    )
            );
        } catch (IOException e) {
            log.error("로그인 요청 처리 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("로그인 요청 처리 실패", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");

        // 사용자 정보와 역할 추출
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String userEmail = userDetails.getUsername(); // 이메일 사용
        UserRoleEnum role = userDetails.getUser().getRole();

        // JWT 생성 및 응답 헤더에 추가
        String token = jwtUtil.createToken(userEmail, role);
        jwtUtil.addJwtToHeader(token, response);

        log.info("JWT가 헤더에 추가되었습니다.");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
