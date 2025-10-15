package com.example.aitalk.domain.community.comment;

import com.example.aitalk.domain.community.post.CommunityPost;
import com.example.aitalk.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommunityPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String content;

    @CreationTimestamp // 자동으로 생성 시간 저장
    private LocalDateTime createdAt;
}
