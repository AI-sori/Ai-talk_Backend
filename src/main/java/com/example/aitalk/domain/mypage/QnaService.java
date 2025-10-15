package com.example.aitalk.domain.mypage;

import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.member.MemberRepository;
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
    private final MemberRepository memberRepository;

    public QnaResponseDTO createQna(QnaRequestDTO dto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버 없음"));

        Qna qna = new Qna(dto.getTitle(), dto.getContent(), member);
        Qna savedQna = qnaRepository.save(qna);

        return new QnaResponseDTO(savedQna);
    }

    public List<QnaResponseDTO> getMyQnas(Member member) {
        return qnaRepository.findByMember(member).stream()
                .map(QnaResponseDTO::new)
                .toList();
    }

    public QnaResponseDTO getQna(Long id) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문의사항 없음"));
        return new QnaResponseDTO(qna);
    }

    @Transactional
    public void updateQna(Long id, QnaRequestDTO dto, Long memberId) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문의사항 없음"));

        if (!qna.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("작성자만 수정 가능");
        }
        if (qna.isReplied()) {
            throw new IllegalStateException("답변이 등록된 문의는 수정 불가");
        }

        qna.update(dto.getTitle(), dto.getContent());
    }

    public void deleteQna(Long id, Long memberId) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문의사항 없음"));

        if (!qna.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("작성자만 삭제 가능");
        }
        if (qna.isReplied()) {
            throw new IllegalStateException("답변이 등록된 문의는 삭제 불가");
        }

        qnaRepository.delete(qna);
    }
}
