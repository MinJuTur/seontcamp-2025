package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class RoleTestController { //테스트용 컨트롤러

    @GetMapping("/user")
    public ResponseEntity<String> userOnlyAccess() {
        return ResponseEntity.ok("✅ USER 또는 ADMIN 역할만 접근 가능합니다.");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminOnlyAccess() {
        return ResponseEntity.ok("🔐 ADMIN 역할만 접근 가능합니다.");
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicAccess() {
        return ResponseEntity.ok("🌐 모두에게 공개된 엔드포인트입니다.");
    }
}