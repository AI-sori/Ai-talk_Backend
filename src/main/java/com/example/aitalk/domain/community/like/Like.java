package com.example.aitalk.domain.community.like;

import com.example.aitalk.domain.community.post.CommunityPost;
import com.example.aitalk.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

