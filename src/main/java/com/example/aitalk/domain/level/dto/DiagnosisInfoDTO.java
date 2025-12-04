package com.example.aitalk.domain.level.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiagnosisInfoDTO {
	private String date;

	@JsonProperty("reading_time_seconds")
	private Double readingTimeSeconds;
}