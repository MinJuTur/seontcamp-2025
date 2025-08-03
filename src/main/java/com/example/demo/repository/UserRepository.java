package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUserId(String userId);  // 회원가입 시 중복 아이디 체크
    Optional<User> findByUserId(String userId); // 로그인 시 해당 아이디의 사용자 존재 여부 확인
}
