package com.example.aitalk.domain.program;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProgramCategory {
	CONCENTRATION("집중력"),
	CLARITY("명확성"),
	FLUENCY("유창성");

	private final String description;
}