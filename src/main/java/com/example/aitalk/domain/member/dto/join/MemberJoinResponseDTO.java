package com.example.aitalk.domain.member.dto.join;


import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor // 모든 필드를 포함하는 생성자가 자동 생성
public class MemberJoinResponseDTO {

    int statusCode; // 응답 상태 코드

    String email; // 가입한 사용자 이메일

    String message; // 응답 메시지

    public MemberJoinResponseDTO() {

    }
}
