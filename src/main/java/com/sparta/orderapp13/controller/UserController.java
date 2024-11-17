// UserController.java
package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.LoginRequestDto;
import com.sparta.orderapp13.dto.SignupRequestDto;
import com.sparta.orderapp13.dto.JwtResponse;  // JwtResponse 임포트
import com.sparta.orderapp13.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        // 로그인 시 JWT 토큰을 생성하고 반환받음
        String token = userService.login(requestDto, response);

        // JWT 토큰을 JwtResponse 객체에 담아서 반환
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('MASTER')")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    @PatchMapping("/assign-manager")
    public ResponseEntity<String> assignManager(@RequestParam String username) {
        userService.assignManager(username);
        return ResponseEntity.ok("사용자 " + username + "이(가) MANAGER로 임명되었습니다.");
    }
}
