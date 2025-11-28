package com.example.aitalk.domain.level.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelAssessmentRequestDTO {
	private String level;
	@JsonProperty("total_score")
	private Double totalScore;
	private Double concentration;
	private Double clarity;
	private Double fluency;
	private String issues;
	@JsonProperty("weak_area")
	private String weakArea;
}