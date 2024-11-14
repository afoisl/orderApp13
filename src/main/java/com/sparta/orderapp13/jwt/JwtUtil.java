package com.sparta.orderapp13.jwt;

import com.sparta.orderapp13.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}")
    private String secretKey;  // application.properties에서 JWT secret key 값을 받아옴
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;  // 서명 알고리즘

    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    // Secret Key 초기화
    @PostConstruct
    public void init() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            this.key = Keys.hmacShaKeyFor(keyBytes);  // Base64로 인코딩된 secretKey를 사용하여 Key 생성
        } catch (IllegalArgumentException e) {
            logger.error("JWT Secret Key 초기화 중 오류 발생: 올바른 Base64 인코딩 문자열을 사용해야 합니다.");
            throw new IllegalStateException("JWT Secret Key가 유효하지 않습니다.");
        }
    }

    // JWT 토큰 생성 메서드
    public String createToken(String username, UserRoleEnum role) {
        Date now = new Date();

        // JWT 생성
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)  // 사용자 이메일을 subject로 설정
                        .claim("role", role.name())  // 사용자 역할을 claim에 추가
                        .setExpiration(new Date(now.getTime() + TOKEN_TIME))  // 만료시간 설정
                        .setIssuedAt(now)  // 발행 시간 설정
                        .signWith(key, signatureAlgorithm)  // 서명
                        .compact();  // JWT 토큰 반환
    }

    // 토큰에서 "Bearer " 부분을 제거하는 메서드
    public String substringToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());  // "Bearer " 제거 후 반환
        }
        return null;
    }

    // 토큰 유효성 검증 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);  // 토큰 검증
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }

    // 토큰에서 사용자 정보 가져오기 메서드
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();  // 토큰에서 claims 추출
    }

    // HTTP 요청에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);  // "Bearer " 이후의 토큰 반환
        }
        return null;
    }
}
