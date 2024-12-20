package com.sparta.orderapp13.security;

import com.sparta.orderapp13.jwt.JwtUtil;
import com.sparta.orderapp13.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

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

        if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
            Claims claims = jwtUtil.getUserInfoFromToken(token);
            String username = claims.getSubject();
            String role = claims.get("role", String.class); // JWT의 클레임에서 role 읽기

            setAuthentication(username, role);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username, String role) {
        // 권한(role)을 GrantedAuthority로 변환
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        // UserDetailsService를 통해 UserDetails 로드
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Authentication 객체 생성 (UserDetails 포함)
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, List.of(authority));

        // SecurityContext에 Authentication 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        log.info("SecurityContext 인증 객체 설정 완료: 사용자={}, 권한={}, UserDetails={}",
                username, authority, userDetails);
    }

}
