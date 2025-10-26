package com.example.aitalk.domain.member.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberProfileUpdateRequestDTO {
	private String nickname;
	private MultipartFile profileImage;
}
