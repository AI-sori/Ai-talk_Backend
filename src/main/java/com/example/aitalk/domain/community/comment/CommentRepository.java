package com.example.aitalk.domain.community.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aitalk.domain.community.post.CommunityPost;
import com.example.aitalk.domain.member.Member;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostId(Long postId);

	List<Comment> findByMember(Member member);

	int countByPost(CommunityPost post);
}
