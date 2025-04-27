package com.example.aitalk.community.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MyPageCommentResponseDTO {
    private Long commentId;
    private String postTitle; // 댓글을 단 게시글 제목
    private String content; // 댓글 내용
    private LocalDateTime createdAt;
}
