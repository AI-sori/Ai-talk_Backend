package com.example.aitalk.domain.program;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
	List<Program> findAllByOrderByIdDesc(); // ID 내림차순 전체 조회

	List<Program> findByCategoryOrderByIdDesc(String category); // 카테고리별 조회 + 내림차순

}
