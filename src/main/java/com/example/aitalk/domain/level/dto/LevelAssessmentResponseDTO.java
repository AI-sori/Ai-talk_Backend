package com.example.aitalk.domain.level.dto;

import java.time.LocalDateTime;

import com.example.aitalk.domain.level.LevelAssessment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class LevelAssessmentResponseDTO {
	private Long id;
	private String level;
	private Double totalScore;
	private Double concentration;
	private Double clarity;
	private Double fluency;
	private String issues;
	private String weakArea;
	private LocalDateTime assessedAt;

	public static LevelAssessmentResponseDTO from(LevelAssessment assessment) {
		return LevelAssessmentResponseDTO.builder()
			.id(assessment.getId())
			.level(assessment.getLevel())
			.totalScore(assessment.getTotalScore())
			.concentration(assessment.getConcentration())
			.clarity(assessment.getClarity())
			.fluency(assessment.getFluency())
			.issues(assessment.getIssues())
			.weakArea(assessment.getWeakArea())
			.assessedAt(assessment.getAssessedAt())
			.build();
	}
}