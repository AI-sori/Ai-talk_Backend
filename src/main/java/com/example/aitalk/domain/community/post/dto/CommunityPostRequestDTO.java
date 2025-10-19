package com.example.aitalk.domain.community.post.dto;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityPostRequestDTO {
	@NotBlank(message = "카테고리는 필수로 선택해주세요.")
	private String category;

	@NotBlank(message = " 제목은 필수로 입력해주세요.")
	private String title;
	@NotBlank(message = " 내용은 필수로 입력해주세요.")
	private String content;
	@Nullable
	private MultipartFile profileImage;
}
