package com.example.aitalk.domain.level;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable // JPA에게 이 클래스가 다른 엔티티에 '내장'될 수 있음을 알림
public class LevelAssessment {

	private String level;

	private Double totalScore;
	private Double concentration;
	private Double clarity;
	private Double fluency;
	private String issues;
	private String weakArea;
}