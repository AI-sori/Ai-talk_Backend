package com.example.aitalk.domain.level;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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