package com.example.aitalk.member;

import com.example.aitalk.security.Response;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor // 모든 필드를 초기화하는 생성자 자동 생성 (의존성 주입)
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    /* 회원가입 진행 */
    @PostMapping("/join")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "401", description = "이미 가입된 회원")
    public Response<MemberJoinResponseDTO> join(@RequestBody @Valid MemberJoinRequestDTO memberJoinRequestDTO) {
        // 회원가입 서비스 호출
        MemberJoinResponseDTO memberJoinResponseDTO = memberService.join(memberJoinRequestDTO);

        // 회원가입 실패 시 (이미 가입된 회원)
        if (memberJoinResponseDTO.getStatusCode() == 401) {
            return Response.error(memberJoinResponseDTO); // ✅ DTO 전체 반환 가능
        }
        // 회원가입 성공 시
        return Response.success(memberJoinResponseDTO);
    }

    /* 로그인 */
    @PostMapping("/login")
    public Response<MemberLoginResponseDTO> login(@RequestBody MemberLoginRequestDTO memberLoginRequestDTO) {
        // 로그인 서비스 호출
        MemberLoginResponseDTO memberLoginResponseDTO = memberService.login(memberLoginRequestDTO);
        //login() 메서드에 @RequestBody 내용을 MemberLoginRequestDTO에 담아 호출한다

        // 로그인 실패 시 (아이디 없음 또는 비밀번호 불일치)
        if (memberLoginResponseDTO.getStatusCode() == 401) {
            return Response.error(memberLoginResponseDTO);
        }
        // 로그인 성공 시
        return Response.success(memberLoginResponseDTO);

        // MemberService에서 받은 MemberResponseDTO를 클라이언트에 보낸다.
    }
}
