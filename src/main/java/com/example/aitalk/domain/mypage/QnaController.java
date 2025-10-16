package com.example.aitalk.domain.mypage;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.mypage.dto.QnaRequestDTO;
import com.example.aitalk.domain.mypage.dto.QnaResponseDTO;
import com.example.aitalk.global.util.ResponseUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

	private final QnaService qnaService;

	@PostMapping
	public ResponseEntity<CommonResponse<Void>> createQna(@RequestBody QnaRequestDTO dto,
		@AuthenticationPrincipal Member loginUser) {

		qnaService.createQna(dto, loginUser);

		return ResponseUtil.success(null);
	}

	@GetMapping
	public ResponseEntity<CommonResponse<List<QnaResponseDTO>>> getMyQnas(@AuthenticationPrincipal Member loginUser) {
		return ResponseUtil.success(qnaService.getMyQnas(loginUser));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CommonResponse<QnaResponseDTO>> getQna(@PathVariable Long id) {
		return ResponseUtil.success(qnaService.getQna(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CommonResponse<Void>> updateQna(
		@PathVariable Long id,
		@RequestBody QnaRequestDTO dto,
		@AuthenticationPrincipal Member loginUser
	) {
		qnaService.updateQna(id, dto, loginUser.getId());

		return ResponseUtil.success(null);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CommonResponse<Void>> deleteQna(
		@PathVariable Long id,
		@AuthenticationPrincipal Member loginUser
	) {
		qnaService.deleteQna(id, loginUser.getId());

		return ResponseUtil.success(null);
	}
}

