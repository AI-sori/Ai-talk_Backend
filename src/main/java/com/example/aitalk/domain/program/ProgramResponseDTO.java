package com.example.aitalk.domain.program;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProgramResponseDTO {

	private Long id;
	private String level;
	private String category;
	private String description;
	private String videoUrl;

	// 정적 팩토리 메서드: Entity -> DTO 변환
	public static ProgramResponseDTO from(Program program) {
		return ProgramResponseDTO.builder()
			.id(program.getId())
			.level(program.getLevel().name()) // Enum 객체의 name() 메서드를 호출하여 영문 문자열로 변환
			.category(program.getCategory().name())
			.description(program.getDescription())
			.videoUrl(program.getVideoUrl())
			.build();
	}
}