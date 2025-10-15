package com.example.aitalk.domain.mypage.dto;

import com.example.aitalk.domain.mypage.Qna;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String reply;
    private Long userId;

    public QnaResponseDTO(Qna qna) {
        this.id = qna.getId();
        this.title = qna.getTitle();
        this.content = qna.getContent();
        this.reply = qna.getReply();
        this.userId = qna.getMember().getId();
    }
}

