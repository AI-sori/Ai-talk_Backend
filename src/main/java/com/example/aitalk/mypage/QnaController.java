package com.example.aitalk.mypage;

import com.example.aitalk.config.Response;
import com.example.aitalk.member.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    // 문의사항 등록
    @PostMapping
    public ResponseEntity<String> createQna(@RequestBody QnaRequestDTO dto, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        qnaService.createQna(dto, loginUser.getId());
        return ResponseEntity.ok("문의사항 등록 완료");
    }

    // 단건 조회
    @GetMapping("/{qnaId}")
    public ResponseEntity<QnaResponseDTO> getQna(@PathVariable Long qnaId) {
        QnaResponseDTO dto = qnaService.getQna(qnaId);
        return ResponseEntity.ok(dto);
    }

    // 수정 (작성자만 가능, 답변 달리면 불가)
    @PutMapping("/{qnaId}")
    public ResponseEntity<String> updateQna(@PathVariable Long qnaId, @RequestBody QnaRequestDTO dto, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        try {
            qnaService.updateQna(qnaId, dto, loginUser.getId());
            return ResponseEntity.ok("문의사항 수정 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // 삭제 (작성자만 가능, 답변 달리면 불가)
    @DeleteMapping("/{qnaId}")
    public ResponseEntity<String> deleteQna(@PathVariable Long qnaId, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        try {
            qnaService.deleteQna(qnaId, loginUser.getId());
            return ResponseEntity.ok("문의사항 삭제 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/my-qna")
    public ResponseEntity<?> getMyQnas(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }
        return ResponseEntity.ok(qnaService.getMyQnas(loginUser.getId()));
    }
}

