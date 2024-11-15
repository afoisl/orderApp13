package com.sparta.orderapp13.security;

import com.sparta.orderapp13.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.getJwtFromHeader(request);

        if (StringUtils.hasText(token)) {
            // JWT 검증
            if (!jwtUtil.validateToken(token)) {
                log.error("Invalid JWT Token");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 사용자 정보 추출
            Claims claims = jwtUtil.getUserInfoFromToken(token);
            String username = claims.getSubject();

            // Spring Security 인증 설정
            setAuthentication(username);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    // 인증 처리
    private void setAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // SecurityContext에 인증 객체 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
