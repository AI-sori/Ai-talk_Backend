package com.example.aitalk.domain.member;

import io.swagger.v3.oas.annotations.Operation;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.member.dto.MemberJoinRequestDTO;
import com.example.aitalk.domain.member.dto.MemberLoginRequestDTO;
import com.example.aitalk.domain.member.dto.MemberProfileResponseDTO;
import com.example.aitalk.domain.member.dto.MemberProfileUpdateRequestDTO;
import com.example.aitalk.global.util.ResponseUtil;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/join")
    @ApiResponse(responseCode = "200", description = "성공")
    // @ApiResponse(responseCode = "401", description = "이미 가입된 회원")
    public ResponseEntity<CommonResponse<Void>> join(@ModelAttribute @Valid MemberJoinRequestDTO memberJoinRequestDTO) throws IOException {
        memberService.join(memberJoinRequestDTO);

        return ResponseUtil.success(null);
    }

    // 로그인
    @Operation(summary = "로그인", description = "이메일과 비밀번호를 사용해 로그인합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    // @ApiResponse(responseCode = "401", description = "로그인 실패 (이메일 또는 비밀번호 불일치)")
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> login(
        @RequestBody MemberLoginRequestDTO memberLoginRequestDTO,
        HttpSession session) { // 세션 관리를 위해 HttpSession 유지

        Member member = memberService.login(memberLoginRequestDTO);

        session.setAttribute("loginUser", member);

        return ResponseUtil.success(null);
    }


    // 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<CommonResponse<MemberProfileResponseDTO>> getProfile(@AuthenticationPrincipal Member member) {
        MemberProfileResponseDTO profile = memberService.getProfile(member);
        return ResponseUtil.success(profile);
    }

    // 프로필 수정
    @PutMapping("/profile")
    public ResponseEntity<CommonResponse<String>> updateProfile(
        @AuthenticationPrincipal Member member,
        @ModelAttribute @Valid MemberProfileUpdateRequestDTO updateRequestDTO) throws IOException {

        memberService.updateProfile(member, updateRequestDTO);
        return ResponseUtil.success(null);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "현재 로그인한 사용자의 세션을 만료시킵니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    // @ApiResponse(responseCode = "400", description = "로그인된 사용자 없음")
    public ResponseEntity<CommonResponse<String>> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseUtil.success(null);
    }

    // 회원 탈퇴
    @DeleteMapping("/delete")
    @Operation(summary = "회원 탈퇴", description = "로그인한 사용자의 계정을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공")
    // @ApiResponse(responseCode = "400", description = "로그인된 사용자 없음")
    public ResponseEntity<CommonResponse<String>> delete(@AuthenticationPrincipal Member member,
        HttpSession session) {

        memberService.delete(member);
        session.invalidate(); // 세션 만료

        return ResponseUtil.success(null);
    }
}
