package com.example.aitalk.domain.mypage;

import com.example.aitalk.api.exception.BusinessException;
import com.example.aitalk.api.exception.ErrorCode;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.mypage.dto.QnaRequestDTO;
import com.example.aitalk.domain.mypage.dto.QnaResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

	private final QnaRepository qnaRepository;

	public void createQna(QnaRequestDTO dto, Member member) {
		Qna qna = new Qna(dto.getTitle(), dto.getContent(), member);
		qnaRepository.save(qna);
	}

	@Transactional(readOnly = true)
	public List<QnaResponseDTO> getMyQnas(Member member) {
		return qnaRepository.findByMember(member).stream().map(QnaResponseDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public QnaResponseDTO getQna(Long id) {
		Qna qna = getQnaOrThrow(id);
		return new QnaResponseDTO(qna);
	}

	@Transactional
	public void updateQna(Long id, QnaRequestDTO dto, Long memberId) {
		Qna qna = getQnaOrThrow(id);
		validateQnaOwner(qna, memberId);
		validateNotReplied(qna);

		qna.update(dto.getTitle(), dto.getContent());
	}

	public void deleteQna(Long id, Long memberId) {
		Qna qna = getQnaOrThrow(id);
		validateQnaOwner(qna, memberId);
		validateNotReplied(qna);

		qnaRepository.delete(qna);
	}

	private Qna getQnaOrThrow(Long qnaId) {
		return qnaRepository.findById(qnaId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_QNA));
	}

	private void validateQnaOwner(Qna qna, Long userId) {
		if (!qna.getMember().getId().equals(userId)) {
			throw new BusinessException(ErrorCode.NO_PERMISSION);
		}
	}

	private void validateNotReplied(Qna qna) {
		if (qna.isReplied()) {
			throw new BusinessException(ErrorCode.ALREADY_REPLIED);
		}
	}
}
