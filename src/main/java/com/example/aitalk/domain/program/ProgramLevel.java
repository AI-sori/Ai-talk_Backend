package com.example.aitalk.domain.program;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProgramLevel {
	BEGINNER("초급"),
	INTERMEDIATE("중급"),
	ADVANCED("고급");

	private final String description; // 한국어 설명을 저장하기 위한 필드
}