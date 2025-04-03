package com.example.aitalk.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Table(name = "member") // 테이블 이름 지정
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // DB 컬럼명과 매칭
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false) // 컬럼명: pw → password로 변경
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profileImage")
    private String profileImage;

    @CreationTimestamp // 생성 시 자동 시간 입력
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

}