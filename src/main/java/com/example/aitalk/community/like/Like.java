package com.example.aitalk.community.like;

import com.example.aitalk.community.CommunityPost;
import com.example.aitalk.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "post_id"}))
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommunityPost post;

    public Like(Member member, CommunityPost post) {
        this.member = member;
        this.post = post;
    }
}

