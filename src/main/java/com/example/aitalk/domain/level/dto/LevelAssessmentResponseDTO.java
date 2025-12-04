package com.example.aitalk.domain.level.dto;

import java.time.LocalDate;

import com.example.aitalk.domain.level.LevelAssessment;

import lombok.Builder;
import lombok.Getter;

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

	private LocalDate assessedDate;
	private Double readingTimeSeconds;

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
			.assessedDate(assessment.getAssessedDate())
			.readingTimeSeconds(assessment.getReadingTimeSeconds())
			.build();
	}
}
