package com.example.aitalk.domain.level;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.level.dto.AiDataRequestDTO;
import com.example.aitalk.global.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class LevelController {
	private final LevelAssessmentService levelAssessmentService;

	@PostMapping("/ai/receive")
	@Operation(summary = "AI 학습 평가 데이터 수신 및 저장", description = "Flask 서버로부터 사용자 학습 평가 데이터를 받아 DB에 저장")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiResponse(responseCode = "400", description = "요청 데이터 오류 또는 사용자 없음")
	public ResponseEntity<CommonResponse<Void>> receiveAiData(@Valid @RequestBody AiDataRequestDTO aiData) {

		levelAssessmentService.saveAiAssessment(aiData);

		return ResponseUtil.success(null);
	}
}