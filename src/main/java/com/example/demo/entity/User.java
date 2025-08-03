package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId; // 사용자가 입력하는 고유한 로그인 ID(중복 불가)

    @Column(nullable = false)
    private String password; // 인코딩된 비밀번호 저장

    private String name; // 사용자 이름
    private String address; // 사용자 주소
}
