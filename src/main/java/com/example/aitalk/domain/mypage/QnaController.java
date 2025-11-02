package com.example.aitalk.domain.mypage;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.member.MemberDetails;
import com.example.aitalk.domain.mypage.dto.QnaRequestDTO;
import com.example.aitalk.domain.mypage.dto.QnaResponseDTO;
import com.example.aitalk.global.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

	private final QnaService qnaService;

	@PostMapping
	@Operation(summary = "qna 작성", description = "현재 로그인한 사용자가 qna를 작성합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<Void>> createQna(@RequestBody QnaRequestDTO dto,
		@AuthenticationPrincipal MemberDetails memberDetails) {

		qnaService.createQna(dto, memberDetails.getMember());

		return ResponseUtil.success(null);
	}

	@GetMapping
	@Operation(summary = "qna 목록 조회", description = "작성된 qna 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<List<QnaResponseDTO>>> getMyQnas(
		@AuthenticationPrincipal MemberDetails memberDetails) {
		return ResponseUtil.success(qnaService.getMyQnas(memberDetails.getMember()));
	}

	@GetMapping("/{id}")
	@Operation(summary = "qna 단일 조회", description = "작성된 개별 qna를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<QnaResponseDTO>> getQna(@PathVariable Long id) {
		return ResponseUtil.success(qnaService.getQna(id));
	}

	@PutMapping("/{id}")
	@Operation(summary = "qna 수정", description = "작성된 qna를 수정합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<Void>> updateQna(
		@PathVariable Long id,
		@RequestBody QnaRequestDTO dto,
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		qnaService.updateQna(id, dto, memberDetails.getMember().getId());

		return ResponseUtil.success(null);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "qna 삭제", description = "작성된 qna를 삭제합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<Void>> deleteQna(
		@PathVariable Long id,
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		qnaService.deleteQna(id, memberDetails.getMember().getId());

		return ResponseUtil.success(null);
	}
}

