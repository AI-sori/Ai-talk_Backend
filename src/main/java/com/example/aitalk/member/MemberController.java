package com.example.aitalk.member;

import com.example.aitalk.security.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor // 모든 필드를 초기화하는 생성자 자동 생성 (의존성 주입)
@RequestMapping("/members")
public class MemberRestController {
    private final MemberService memberService;

    /* 회원가입 진행 */
    @PostMapping("/join")
    public Response<MemberJoinResponseDTO> join(@RequestBody MemberJoinRequestDTO memberJoinRequestDTO) {
        // 회원가입 서비스 호출
        MemberJoinResponseDTO memberJoinResponseDTO = memberService.join(memberJoinRequestDTO);

        // 회원가입 실패 시 (이미 가입된 회원)
        if (memberJoinResponseDTO.getStatusCode() == 401) {
            return Response.error(memberJoinResponseDTO);
        }
        // 회원가입 성공 시
        return Response.success(memberJoinResponseDTO);
    }

    /* 로그인 */
    @PostMapping("/login")
    public Response<MemberLoginResponseDTO> login(@RequestBody MemberLoginRequestDTO memberLoginRequestDTO) {
        // 로그인 서비스 호출
        MemberLoginResponseDTO memberLoginResponseDTO = memberService.login(memberLoginRequestDTO);

        // 로그인 실패 시 (아이디 없음 또는 비밀번호 불일치)
        if (memberLoginResponseDTO.getStatusCode() == 401) {
            return Response.error(memberLoginResponseDTO);
        }
        // 로그인 성공 시
        return Response.success(memberLoginResponseDTO);
    }
}
