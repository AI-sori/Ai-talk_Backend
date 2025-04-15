package com.example.aitalk.community.comment;

import com.example.aitalk.member.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<String> createComment(
            @RequestBody CommentRequestDTO dto,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        commentService.createComment(dto, loginUser.getId());
        return ResponseEntity.ok("댓글 등록 완료");
    }

//    // 댓글 목록 조회
//    @GetMapping("/comments/{postId}")
//    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long postId) {
//        List<CommentResponseDTO> comments = commentService.getComments(postId);
//        return ResponseEntity.ok(comments);
//    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDTO dto,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        try {
            commentService.updateComment(commentId, dto.getContent(), loginUser.getId());
            return ResponseEntity.ok("댓글 수정 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        try {
            commentService.deleteComment(commentId, loginUser.getId());
            return ResponseEntity.ok("댓글 삭제 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
