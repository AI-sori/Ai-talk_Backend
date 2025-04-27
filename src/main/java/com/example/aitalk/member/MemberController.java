package com.example.aitalk.member;

import com.example.aitalk.security.Response;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Response<MemberLoginResponseDTO> login(
            @RequestBody MemberLoginRequestDTO memberLoginRequestDTO,
            HttpSession session) { // 세션 주입

        MemberLoginResponseDTO memberLoginResponseDTO = memberService.login(memberLoginRequestDTO);

        if (memberLoginResponseDTO.getStatusCode() == 401) {
            return Response.error(memberLoginResponseDTO);
        }

        // 로그인 성공 시 실제 사용자 객체(Member)를 세션에 저장
        // 이메일로 `Member` 객체를 찾고, 세션에 저장
        Member member = memberService.findByEmail(memberLoginResponseDTO.getEmail());
        session.setAttribute("loginUser", member); // 전체 객체 저장

        return Response.success(memberLoginResponseDTO);
    }


    /* 프로필 조회 */
    @GetMapping("/profile")
    public Response<MemberProfileResponseDTO> getProfile(@SessionAttribute("loginUser") Member member) {
        MemberProfileResponseDTO profile = memberService.getProfile(member);
        return Response.success(profile);
    }

    /* 프로필 수정 */
    @PutMapping("/profile")
    public Response<String> updateProfile(
            @SessionAttribute("loginUser") Member member,
            @RequestBody @Valid MemberProfileUpdateRequestDTO updateRequestDTO) {

        memberService.updateProfile(member, updateRequestDTO);
        return Response.success("프로필이 성공적으로 수정되었습니다.");
    }
}
