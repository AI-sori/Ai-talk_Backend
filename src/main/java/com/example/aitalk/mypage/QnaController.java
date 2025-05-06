package com.example.aitalk.mypage;

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

    @PostMapping
    public ResponseEntity<QnaResponseDTO> createQna(@RequestBody QnaRequestDTO dto, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        QnaResponseDTO responseDto = qnaService.createQna(dto, loginUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<QnaResponseDTO>> getMyQnas(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(qnaService.getMyQnas(loginUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QnaResponseDTO> getQna(@PathVariable Long id) {
        return ResponseEntity.ok(qnaService.getQna(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateQna(
            @PathVariable Long id,
            @RequestBody QnaRequestDTO dto,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            qnaService.updateQna(id, dto, loginUser.getId());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQna(@PathVariable Long id, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        try {
            qnaService.deleteQna(id, loginUser.getId());
            return ResponseEntity.ok("문의사항 삭제 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}

