package com.example.aitalk.domain.member;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.member.dto.MemberJoinRequestDTO;
import com.example.aitalk.domain.member.dto.MemberLoginRequestDTO;
import com.example.aitalk.domain.member.dto.MemberProfileResponseDTO;
import com.example.aitalk.domain.member.dto.MemberProfileUpdateRequestDTO;
import com.example.aitalk.global.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberService;

	// 회원가입
	@PostMapping(value = "/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<Void>> join(
		@RequestPart("memberInfo") @Valid MemberJoinRequestDTO memberInfo,
		@RequestPart("profileImage") MultipartFile profileImage
	) throws IOException {
		memberService.join(memberInfo, profileImage);

		return ResponseUtil.success(null);
	}

	// 로그인
	@Operation(summary = "로그인", description = "이메일과 비밀번호를 사용해 로그인합니다.")
	@ApiResponse(responseCode = "200", description = "로그인 성공")
	@PostMapping("/login")
	public ResponseEntity<CommonResponse<Void>> login(
		@RequestBody MemberLoginRequestDTO memberLoginRequestDTO,
		HttpSession session) { // 세션 관리를 위해 HttpSession 유지

		Member member = memberService.login(memberLoginRequestDTO);

		session.setAttribute("loginUser", member);

		return ResponseUtil.success(null);
	}

	// 프로필 조회
	@GetMapping("/profile")
	public ResponseEntity<CommonResponse<MemberProfileResponseDTO>> getProfile(@AuthenticationPrincipal Member member) {
		MemberProfileResponseDTO profile = memberService.getProfile(member);
		return ResponseUtil.success(profile);
	}

	// 프로필 수정
	@PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CommonResponse<String>> updateProfile(
		@AuthenticationPrincipal Member member,
		@ModelAttribute @Valid MemberProfileUpdateRequestDTO updateRequestDTO) throws IOException {

		memberService.updateProfile(member, updateRequestDTO);
		return ResponseUtil.success(null);
	}

	@PostMapping("/logout")
	@Operation(summary = "로그아웃", description = "현재 로그인한 사용자의 세션을 만료시킵니다.")
	@ApiResponse(responseCode = "200", description = "로그아웃 성공")
	public ResponseEntity<CommonResponse<String>> logout(HttpSession session) {
		session.invalidate(); // 세션 무효화
		return ResponseUtil.success(null);
	}

	// 회원 탈퇴
	@DeleteMapping("/delete")
	@Operation(summary = "회원 탈퇴", description = "로그인한 사용자의 계정을 삭제합니다.")
	@ApiResponse(responseCode = "200", description = "회원 탈퇴 성공")
	public ResponseEntity<CommonResponse<String>> delete(@AuthenticationPrincipal Member member,
		HttpSession session) {

		memberService.delete(member);
		session.invalidate(); // 세션 만료

		return ResponseUtil.success(null);
	}
}
