package com.example.aitalk.domain.program;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/program")
public class ProgramController {

	private final ProgramRepository programRepo;

	public ProgramController(ProgramRepository programRepo) {
		this.programRepo = programRepo;
	}

	// 전체 조회
	@GetMapping
	public List<Program> getAllPrograms() {
		return programRepo.findAllByOrderByIdDesc(); // 내림차순 반환
	}

	@GetMapping("/{category}")
	public List<Program> getProgramsByCategory(@PathVariable String category) {
		return programRepo.findByCategoryOrderByIdDesc(category); // 카테고리별 + 내림차순
	}
}
