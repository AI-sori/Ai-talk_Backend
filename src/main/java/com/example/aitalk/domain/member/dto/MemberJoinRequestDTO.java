package com.example.aitalk.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class MemberJoinRequestDTO {

	// 유효성 검사
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "올바른 이메일 형식이어야 합니다.")
	private String email; // 이메일 (VARCHAR(255), UNIQUE)

	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	private String password; // 비밀번호 (VARCHAR(255))

	private String nickname; // 닉네임 (NULL 허용, VARCHAR(255))
}