package com.example.aitalk.domain.level;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.aitalk.api.exception.BusinessException;
import com.example.aitalk.api.exception.ErrorCode;
import com.example.aitalk.domain.level.dto.AiDataRequestDTO;
import com.example.aitalk.domain.level.dto.LevelAssessmentRequestDTO;
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

		Member member = memberRepository.findMemberByEmail(email)
			.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

		LevelAssessment newAssessment = new LevelAssessment(member, assessmentDto);

		levelAssessmentRepository.save(newAssessment);
	}
}