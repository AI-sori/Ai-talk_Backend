package com.example.aitalk.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
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

    @Column(name = "profile_image") // Java에서는 일반적으로 camelCase(profileImage)를, DB에서는 snake_case(profile_image)를 사용
    private String profileImage;

    @CreationTimestamp // 생성 시 자동 시간 입력
    @Column(name = "updated_at", updatable = false)
    private LocalDateTime createdAt;

}