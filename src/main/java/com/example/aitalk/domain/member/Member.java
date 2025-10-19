package com.example.aitalk.domain.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.aitalk.domain.community.comment.Comment;
import com.example.aitalk.domain.community.like.Like;
import com.example.aitalk.domain.community.post.CommunityPost;
import com.example.aitalk.domain.mypage.Qna;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") // DB 컬럼명과 매칭
	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false) // 컬럼명: pw → password로 변경
	private String password;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "profile_image") // Java에서는 일반적으로 camelCase(profileImage)를, DB에서는 snake_case(profile_image)를 사용
	private String profileImage;

	@CreationTimestamp // 생성 시 자동 시간 입력
	@Column(name = "updated_at", updatable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "member",
		cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<CommunityPost> communityPosts = new ArrayList<>();

	@OneToMany(mappedBy = "member",
		cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "member",
		cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<Qna> qnas = new ArrayList<>();

	@OneToMany(mappedBy = "member",
		cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<Like> likes = new ArrayList<>();

}