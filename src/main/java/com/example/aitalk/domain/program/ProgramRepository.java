package com.example.aitalk.domain.program;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {

	//난이도(ProgramLevel)에 해당하는 프로그램 조회
	List<Program> findByLevel(ProgramLevel level);
}
