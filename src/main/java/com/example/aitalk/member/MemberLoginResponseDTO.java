package com.example.aitalk.member;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class MemberLoginResponseDTO {

    int statusCode; // 응답 상태 코드

    String email; // 가입한 사용자 이메일

    String message; // 응답 메시지
    Long userId;
}