package com.sparta.orderapp13.controller;

import com.sparta.orderapp13.dto.LoginRequestDto;
import com.sparta.orderapp13.dto.SignupRequestDto;
import com.sparta.orderapp13.dto.JwtResponse;
import com.sparta.orderapp13.dto.UpdateUserRequestDto;
import com.sparta.orderapp13.entity.User; // User 엔티티 임포트
import com.sparta.orderapp13.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        String token = userService.login(requestDto, response);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }


    // CUSTOMER 사용자에게 MANAGER 역할 할당

    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    @PatchMapping("/assign-owner")
    public ResponseEntity<String> assignOwner(@RequestParam String userEmail) {
        userService.assignOwner(userEmail);
        return ResponseEntity.ok("사용자 " + userEmail + "이(가) OWNER로 임명되었습니다.");
    }

    // 로그인한 사용자의 개인정보 수정 (CUSTOMER/OWNER)
    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UpdateUserRequestDto requestDto) {
        userService.updateUser(requestDto);
        return ResponseEntity.ok("개인정보 수정 완료");
    }

    // MASTER 또는 MANAGER가 다른 사용자의 개인정보 수정
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    @PatchMapping("/update/{userId}")
    public ResponseEntity<String> updateUserByAdmin(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequestDto requestDto) {
        userService.updateUserByAdmin(userId, requestDto);
        return ResponseEntity.ok("사용자 정보 수정 완료");
    }

    // 로그인한 사용자의 계정 삭제 (CUSTOMER/OWNER)
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok("계정 삭제 완료");
    }

    // MASTER 또는 MANAGER가 다른 사용자의 계정 삭제
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserByAdmin(@PathVariable Long userId) {
        userService.deleteUserByAdmin(userId);
        return ResponseEntity.ok("사용자 계정 삭제 완료");
    }

    // MASTER와 MANAGER가 모든 사용자 정보를 조회
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        // 모든 사용자 조회
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 로그인한 사용자의 정보 조회
    @GetMapping("/me")
    public ResponseEntity<User> getMyInfo() {
        // 현재 로그인한 사용자 정보 조회
        User myInfo = userService.getMyInfo();
        return ResponseEntity.ok(myInfo);

    }

}
