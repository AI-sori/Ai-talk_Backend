package com.example.aitalk.domain.level.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelAssessmentRequestDTO {
	private String level;
	private Double total_score;
	private Double concentration;
	private Double clarity;
	private Double fluency;
	private String issues;
	private String weak_area;
}