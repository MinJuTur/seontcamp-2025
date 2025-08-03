package com.example.demo.controller;

import com.example.demo.dto.request.UserJoinRequest;
import com.example.demo.dto.request.UserLoginRequest;
import com.example.demo.dto.response.UserJoinResponse;
import com.example.demo.dto.response.UserLoginResponse;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users") // 공통 prefix
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join") // 회원가입 요청 처리
    public ResponseEntity<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        UserJoinResponse response = userService.register(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login") // 로그인 요청 처리
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUserId(), request.getPassword());
        return ResponseEntity.ok(new UserLoginResponse(token));
    }
}
