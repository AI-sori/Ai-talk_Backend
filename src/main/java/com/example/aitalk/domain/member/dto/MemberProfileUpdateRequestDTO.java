package com.example.aitalk.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MemberProfileUpdateRequestDTO {
    private String nickname;
    private MultipartFile profileImage;
}
