# WINK 여름방학 선트캠프 과제

Spring Boot 기반의 회원가입·로그인 API를 구현하고, JWT 인증/인가와 Kafka 이벤트 메시징을 실습한 백엔드 프로젝트입니다.

사용자는 회원가입 후 로그인할 수 있으며, 로그인 성공 시 JWT 토큰을 발급받습니다. 발급된 토큰은 보호된 API에 접근할 때 사용됩니다. 또한 회원가입 및 로그인 성공 이벤트를 Kafka Topic으로 발행하고 Consumer에서 수신하도록 구성했습니다.

---

## 주요 기능

### 회원 기능
- 회원가입
- 로그인
- 아이디 중복 검증
- 비밀번호 BCrypt 암호화 저장

### 인증 / 인가
- JWT 기반 Stateless 인증
- Spring Security 기반 API 접근 제어
- USER / ADMIN 권한 분리
- Authorization Header 기반 인증 처리

### Kafka 이벤트 처리
- 회원가입 성공 이벤트 발행
- 로그인 성공 이벤트 발행
- Kafka Consumer를 통한 이벤트 수신 확인

---

## 기술 스택

| 구분 | 기술 |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Database | MySQL |
| ORM | Spring Data JPA |
| Security | Spring Security, JWT |
| Template | Thymeleaf |
| Messaging | Apache Kafka |
| Infra | Docker Compose |
| Build Tool | Gradle |

---

## 프로젝트 구조

```bash
src/main/java/com/example/demo
├── config
│   └── SecurityConfig.java
├── controller
│   ├── UserController.java
│   └── RoleTestController.java
├── dto
│   ├── request
│   │   ├── UserJoinRequest.java
│   │   └── UserLoginRequest.java
│   └── response
│       ├── UserJoinResponse.java
│       └── UserLoginResponse.java
├── entity
│   ├── User.java
│   └── UserRole.java
├── exception
│   └── CustomException.java
├── filter
│   └── JwtFilter.java
├── kafka
│   ├── KafkaProducer.java
│   └── KafkaConsumer.java
├── repository
│   └── UserRepository.java
├── service
│   └── UserService.java
├── util
│   └── JwtUtil.java
└── DemoApplication.java
