package com.example.aitalk.mypage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String reply;
    private Long memberId;

    public QnaResponseDTO(Qna qna) {
        this.id = qna.getId();
        this.title = qna.getTitle();
        this.content = qna.getContent();
        this.reply = qna.getReply();
        this.memberId = qna.getMember().getId();
    }
}

