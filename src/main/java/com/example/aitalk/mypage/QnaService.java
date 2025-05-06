package com.example.aitalk.mypage;

import com.example.aitalk.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaService {

    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;

    public QnaService(QnaRepository qnaRepository, MemberRepository memberRepository) {
        this.qnaRepository = qnaRepository;
        this.memberRepository = memberRepository;
    }

    // 문의사항 등록
    public QnaResponseDTO createQna(QnaRequestDTO qnaRequestDTO, Long id) {
        Qna qna = new Qna(qnaRequestDTO.getTitle(), qnaRequestDTO.getContent(), qnaRequestDTO.getMemberId());
        qnaRepository.save(qna);
        return new QnaResponseDTO(qna);
    }

    // 문의사항 수정 (자신이 작성한 것만 수정 가능, 답변이 달린 경우 수정 불가)
    public QnaResponseDTO updateQna(Long qnaId, QnaRequestDTO qnaRequestDTO, Long memberId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new EntityNotFoundException("Qna not found"));

        // 작성자가 아닌 사용자가 수정하려 할 경우
        if (!qna.getMember().getId().equals(memberId)) {
            throw new IllegalStateException("자신이 작성한 문의사항만 수정할 수 있습니다.");
        }

        // 답변이 달린 경우 수정 불가
        if (qna.getReply() != null) {
            throw new IllegalStateException("답변이 달린 후에는 수정할 수 없습니다.");
        }

        qna.setTitle(qnaRequestDTO.getTitle());
        qna.setContent(qnaRequestDTO.getContent());
        qnaRepository.save(qna);
        return new QnaResponseDTO(qna);
    }

    // 문의사항 삭제 (자신이 작성한 것만 삭제 가능, 답변이 달린 경우 삭제 불가)
    public void deleteQna(Long qnaId, Long memberId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new EntityNotFoundException("Qna not found"));

        // 작성자가 아닌 사용자가 삭제하려 할 경우
        if (!qna.getMember().getId().equals(memberId)) {
            throw new IllegalStateException("자신이 작성한 문의사항만 삭제할 수 있습니다.");
        }

        // 답변이 달린 경우 삭제 불가
        if (qna.getReply() != null) {
            throw new IllegalStateException("답변이 달린 후에는 삭제할 수 없습니다.");
        }

        qnaRepository.delete(qna);
    }

    // 문의사항 조회
    public QnaResponseDTO getQna(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new EntityNotFoundException("Qna not found"));
        return new QnaResponseDTO(qna);
    }

    // 🔽 내가 쓴 문의사항 목록 조회
    public List<QnaResponseDTO> getMyQnas(Long memberId) {
        List<Qna> myQnas = qnaRepository.findByMemberId(memberId);
        return myQnas.stream()
                .map(QnaResponseDTO::new)
                .toList();
    }
}