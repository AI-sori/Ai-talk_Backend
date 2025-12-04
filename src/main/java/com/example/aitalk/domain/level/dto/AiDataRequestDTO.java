package com.example.aitalk.domain.level.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiDataRequestDTO {

	@Valid
	@JsonProperty("level_assessment")
	private LevelAssessmentRequestDTO levelAssessment;

	@Valid
	@JsonProperty("user_profile")
	private UserProfileDTO userProfile;

	@Valid
	@JsonProperty("diagnosis_info")
	private DiagnosisInfoDTO diagnosisInfo;
}