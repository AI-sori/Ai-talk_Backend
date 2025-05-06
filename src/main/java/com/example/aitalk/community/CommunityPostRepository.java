package com.example.aitalk.community;

import com.example.aitalk.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    List<CommunityPost> findByMember(Member member);
}
