package com.example.aitalk.domain.level;

import java.time.LocalDateTime;

import com.example.aitalk.domain.level.dto.LevelAssessmentRequestDTO;
import com.example.aitalk.domain.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity
public class LevelAssessment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	private String level;

	private Double totalScore;
	private Double concentration;
	private Double clarity;
	private Double fluency;
	private String issues;
	private String weakArea;

	@Column(nullable = false)
	private LocalDateTime assessedAt = LocalDateTime.now();

	public LevelAssessment(Member member, LevelAssessmentRequestDTO dto) {
		this.member = member;
		this.level = dto.getLevel();
		this.totalScore = dto.getTotalScore();
		this.concentration = dto.getConcentration();
		this.clarity = dto.getClarity();
		this.fluency = dto.getFluency();
		this.issues = dto.getIssues();
		this.weakArea = dto.getWeakArea();
	}
}