package com.example.aitalk.domain.community.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Getter
@Setter
public class CommunityPostRequestDTO {
    private String category;
    private String title;
    private String content;
    private MultipartFile image;
}
