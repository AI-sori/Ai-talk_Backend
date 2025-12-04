package com.example.aitalk.domain.level;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.aitalk.api.exception.BusinessException;
import com.example.aitalk.api.exception.ErrorCode;
import com.example.aitalk.domain.level.dto.AiDataRequestDTO;
import com.example.aitalk.domain.level.dto.DiagnosisInfoDTO;
import com.example.aitalk.domain.level.dto.LevelAssessmentRequestDTO;
import com.example.aitalk.domain.level.dto.LevelAssessmentResponseDTO;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LevelAssessmentService {

	private final MemberRepository memberRepository;
	private final LevelAssessmentRepository levelAssessmentRepository;

	public void saveAiAssessment(AiDataRequestDTO request) {

		String email = request.getUserProfile().getEmail();
		LevelAssessmentRequestDTO assessmentDto = request.getLevelAssessment();
		DiagnosisInfoDTO diagnosisInfo = request.getDiagnosisInfo();

		Member member = memberRepository.findMemberByEmail(email)
			.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

		LevelAssessment newAssessment = new LevelAssessment(member, assessmentDto, diagnosisInfo);

		levelAssessmentRepository.save(newAssessment);
	}

	public List<LevelAssessmentResponseDTO> getAllAssessments(Member member) {

		Member fullyLoadedMember = memberRepository.findByIdWithAssessments(member.getId())
			.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

		List<LevelAssessment> assessments = fullyLoadedMember.getLevelAssessments();

		return assessments.stream()
			.sorted(Comparator.comparing(LevelAssessment::getId))
			.map(LevelAssessmentResponseDTO::from)
			.collect(Collectors.toList());
	}
}