package com.example.aitalk.domain.community.comment;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.aitalk.domain.community.post.CommunityPost;
import com.example.aitalk.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private CommunityPost post;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	private String content;

	@CreationTimestamp // 자동으로 생성 시간 저장
	private LocalDateTime createdAt;
}
