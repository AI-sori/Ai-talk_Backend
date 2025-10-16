package com.example.aitalk.domain.community.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aitalk.domain.member.Member;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
	List<CommunityPost> findByMember(Member member);

	List<CommunityPost> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByIdDesc(
		String titleKeyword, String contentKeyword
	);
}
