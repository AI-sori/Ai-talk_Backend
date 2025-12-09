package com.example.aitalk.domain.community.post.dto;

import java.util.List;

import com.example.aitalk.domain.community.comment.dto.CommentResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityPostResponseDTO {
	private Long postId;
	private String nickname;
	private Long userId;
	private String category;
	private String title;
	private String content;
	private String image;

	private int likeCount;

	private List<CommentResponseDTO> comments;

	private boolean liked;

	private boolean isWriter;

}
