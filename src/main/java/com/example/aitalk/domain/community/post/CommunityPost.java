package com.example.aitalk.domain.community.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.aitalk.domain.community.comment.Comment;
import com.example.aitalk.domain.community.like.Like;
import com.example.aitalk.domain.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CommunityPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Member member;

	private String category;
	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	private String image;
	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Like> likes = new ArrayList<>();

	public int getLikeCount() {
		return likes.size();
	}

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

}
