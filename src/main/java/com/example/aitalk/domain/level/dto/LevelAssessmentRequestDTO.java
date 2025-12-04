package com.example.aitalk.domain.level.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelAssessmentRequestDTO {
	private String level;
	@JsonProperty("total_score")
	private Double totalScore;
	private String issues;
	@JsonProperty("weak_area")
	private String weakArea;

	@Valid
	private ScoresDTO scores;
}
