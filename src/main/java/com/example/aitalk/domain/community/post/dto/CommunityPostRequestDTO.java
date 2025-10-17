package com.example.aitalk.domain.community.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityPostRequestDTO {
	private String category;
	private String title;
	private String content;
}
