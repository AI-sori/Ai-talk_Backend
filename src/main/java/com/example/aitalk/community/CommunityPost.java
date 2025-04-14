package com.example.aitalk.community;

import com.example.aitalk.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    private String category;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String image;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getter/Setter 생성자 등
}
