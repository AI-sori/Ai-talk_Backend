package com.example.aitalk.domain.member.dto;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
	@NotBlank(message = "이메일은 필수로 입력해주세요.")
	@Email(message = "올바른 이메일 형식이어야 합니다.")
	private String email; // 이메일 (VARCHAR(255), UNIQUE)

	@NotBlank(message = "비밀번호는 필수로 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
		message = "비밀번호는 최소 8자에서 최대 20자이며, 영문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.")
	private String password; // 비밀번호 (VARCHAR(255))

	@NotBlank(message = "닉네임은 필수로 입력해주세요.")
	private String nickname;

	@Nullable
	private MultipartFile profileImage;
}