package com.example.aitalk.domain.program;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.aitalk.api.exception.BusinessException;
import com.example.aitalk.api.exception.ErrorCode;
import com.example.aitalk.domain.level.LevelAssessment;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.member.MemberRepository;
import com.example.aitalk.domain.program.dto.ProgramResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProgramService {

	private final ProgramRepository programRepository;
	private final MemberRepository memberRepository;

	public List<ProgramResponseDTO> recommendPrograms(Member member) {

		Member fullyLoadedMember = memberRepository.findByIdWithAssessments(member.getId())
			.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

		LevelAssessment assessment = fullyLoadedMember.getLevelAssessments().stream()
			.sorted(Comparator.comparing(LevelAssessment::getAssessedAt).reversed())
			.findFirst() // 최신 LevelAssessment
			.orElse(null);

		if (assessment == null) {
			throw new BusinessException(ErrorCode.ASSESSMENT_NOT_FOUND);
		}

		ProgramLevel userLevel;
		try {
			userLevel = ProgramLevel.valueOf(assessment.getLevel().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new BusinessException(ErrorCode.INVALID_DATA);
		}

		// [필터링] 사용자 Level에 해당하는 모든 프로그램 조회
		List<Program> filteredPrograms = programRepository.findByLevel(userLevel);
		if (filteredPrograms.isEmpty()) {
			return List.of();
		}

		// 약점 영역 순위 결정 (스코어 기반)
		Map<ProgramCategory, Double> scoreMap = buildWeakAreaScoreMap(assessment);

		// [정렬]
		List<ProgramCategory> prioritizedCategories = scoreMap.entrySet().stream()
			.sorted(Map.Entry.comparingByValue())
			.map(Map.Entry::getKey)
			.collect(Collectors.toList());

		List<Program> recommendedList = new ArrayList<>();

		for (ProgramCategory category : prioritizedCategories) {
			List<Program> categoryPrograms = filteredPrograms.stream()
				.filter(p -> p.getCategory() == category)
				.collect(Collectors.toList());

			categoryPrograms.sort(Comparator.comparing(Program::getId).reversed());

			recommendedList.addAll(categoryPrograms);
		}

		return recommendedList.stream()
			.map(ProgramResponseDTO::from)
			.collect(Collectors.toList());
	}

	private Map<ProgramCategory, Double> buildWeakAreaScoreMap(LevelAssessment assessment) {
		Map<ProgramCategory, Double> map = Stream.of(
			Map.entry(ProgramCategory.CONCENTRATION, assessment.getConcentration()),
			Map.entry(ProgramCategory.CLARITY, assessment.getClarity()),
			Map.entry(ProgramCategory.FLUENCY, assessment.getFluency())
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return map;
	}
}