package com.example.demo.service;

import com.example.demo.dto.request.UserJoinRequest;
import com.example.demo.dto.response.UserJoinResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.kafka.KafkaProducer;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final KafkaProducer kafkaProducer;

    // 회원가입 처리 메서드
    public UserJoinResponse register(UserJoinRequest request) {
        // 중복 아이디 검사
        if (userRepository.existsUserByUserId(request.getUserId())) {
            throw new CustomException("이미 존재하는 아이디입니다.", HttpStatus.CONFLICT.value());
        }

        // 비밀번호는 암호화하여 저장
        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .address(request.getAddress())
                .role(UserRole.USER) // 기본값은 일반 사용자
                .build();
        userRepository.save(user);

        kafkaProducer.send("user-joined", user.getUserId()); // Kafka에 가입 메세지 전송

        return new UserJoinResponse(
                user.getId(),
                user.getUserId(),
                user.getName()
        );
    }

    //로그인 처리 메서드
    public String login(String userId, String password) {
        // 사용자 존재 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException("아이디가 존재하지 않습니다.", HttpStatus.NOT_FOUND.value()));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value());
        }

        kafkaProducer.send("user-logged-in", user.getUserId()); // Kafka에 로그인 성공 메세지 전송

        return jwtUtil.generateToken(userId, user.getRole().name());  // 로그인 성공 시 JWT 토큰 반환
    }
}
