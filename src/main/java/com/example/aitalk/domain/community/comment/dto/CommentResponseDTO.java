package com.example.aitalk.domain.community.comment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
	private Long id;
	private String nickname;
	private Long userId;
	private String content;
	private LocalDateTime createdAt;

}
