package com.example.aitalk.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProfileResponseDTO {
	private String email;
	private String nickname;
	private String profileImage;
}
