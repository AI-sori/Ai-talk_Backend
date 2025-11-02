package com.example.aitalk.domain.program;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/program")
public class ProgramController {

	private final ProgramRepository programRepo;

	public ProgramController(ProgramRepository programRepo) {
		this.programRepo = programRepo;
	}

	// 전체 조회
	@GetMapping
	@Operation(summary = "program 목록 조회", description = "현재 모든 program의 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public List<Program> getAllPrograms() {
		return programRepo.findAllByOrderByIdDesc(); // 내림차순 반환
	}

	@GetMapping("/{category}")
	@Operation(summary = "카테고리 별 program 조회", description = "카테고리 별 program 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public List<Program> getProgramsByCategory(@PathVariable String category) {
		return programRepo.findByCategoryOrderByIdDesc(category); // 카테고리별 + 내림차순
	}
}
