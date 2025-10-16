package com.example.aitalk.domain.community.post.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityPostRequestDTO {
	private String category;
	private String title;
	private String content;
	private MultipartFile image;
}
