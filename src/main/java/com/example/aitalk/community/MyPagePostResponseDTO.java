package com.example.aitalk.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MyPagePostResponseDTO {
    private Long postId;
    private String title;
    private String category;
    private LocalDateTime createdAt;
}
