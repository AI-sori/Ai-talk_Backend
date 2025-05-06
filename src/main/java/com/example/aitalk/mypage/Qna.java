package com.example.aitalk.mypage;

import jakarta.persistence.*;
import com.example.aitalk.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String reply;  // 관리자가 입력한 답변

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 문의한 사용자

    public Qna() {}

    // 생성자 및 메서드들
    public Qna(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public boolean isReplied() {
        return reply != null && !reply.isBlank();
    }

    public Member getMember() {
        return member;
    }
}
