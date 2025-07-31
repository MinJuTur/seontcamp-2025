package com.example.demo.service;

import com.example.demo.dto.request.UserJoinRequest;
import com.example.demo.dto.response.UserJoinResponse;
import com.example.demo.entity.User;
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

    public UserJoinResponse register(UserJoinRequest request) {
        if (userRepository.existsUserByUserId(request.getUserId())) {
            throw new CustomException("이미 존재하는 아이디입니다.", HttpStatus.CONFLICT.value());
        }

        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .address(request.getAddress())
                .build();
        userRepository.save(user);

        kafkaProducer.send("user-joined", user.getUserId());

        return new UserJoinResponse(
                user.getId(),
                user.getUserId(),
                user.getName()
        );
    }

    public String login(String userId, String password) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException("아이디가 존재하지 않습니다.", HttpStatus.NOT_FOUND.value()));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value());
        }

        return jwtUtil.generateToken(userId);
    }
}
