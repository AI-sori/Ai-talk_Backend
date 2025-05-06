package com.example.aitalk.community.like;

import com.example.aitalk.community.CommunityPost;
import com.example.aitalk.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByMemberAndPost(Member member, CommunityPost post);
    Optional<Like> findByMemberAndPost(Member member, CommunityPost post);
    List<Like> findByMember(Member member);
}
