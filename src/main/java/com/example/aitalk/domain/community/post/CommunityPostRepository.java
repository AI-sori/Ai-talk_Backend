package com.example.aitalk.domain.community.post;

import com.example.aitalk.domain.community.post.CommunityPost;
import com.example.aitalk.domain.member.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
	List<CommunityPost> findByMember(Member member);

	List<CommunityPost> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByIdDesc(
		String titleKeyword, String contentKeyword
	);
}
