package com.example.aitalk.community.comment;

import com.example.aitalk.community.CommunityPost;
import com.example.aitalk.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    List<Comment> findByMember(Member member);

    int countByPost(CommunityPost post);
}
