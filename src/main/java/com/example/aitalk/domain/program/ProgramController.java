package com.example.aitalk.domain.program;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.member.MemberDetails;
import com.example.aitalk.domain.program.dto.ProgramResponseDTO;
import com.example.aitalk.global.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/program")
@RequiredArgsConstructor
public class ProgramController {

	private final ProgramService programService;

	// 필터 조회
	@GetMapping
	@Operation(summary = "맞춤형 학습 프로그램 추천 목록 조회", description = "현재 로그인 유저의 Level에 맞춰 필터링되고, 약점 영역 점수에 따라 정렬된 프로그램 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiResponse(responseCode = "400", description = "유효하지 않은 데이터 또는 진단 결과 없음")
	public ResponseEntity<CommonResponse<List<ProgramResponseDTO>>> recommendPrograms(
		@AuthenticationPrincipal MemberDetails memberDetails) {

		Member member = memberDetails.getMember();
		List<ProgramResponseDTO> programs = programService.recommendPrograms(member);

		return ResponseUtil.success(programs);
	}
}