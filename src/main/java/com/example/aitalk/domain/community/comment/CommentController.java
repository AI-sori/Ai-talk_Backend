package com.example.aitalk.domain.community.comment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.community.comment.dto.CommentRequestDTO;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseListDTO;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.member.MemberDetails;
import com.example.aitalk.global.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	// 댓글 생성
	@PostMapping("/comments")
	public ResponseEntity<CommonResponse<Void>> createComment(
		@RequestBody CommentRequestDTO dto,
		@AuthenticationPrincipal MemberDetails loginUser
	) {
		commentService.createComment(dto, loginUser.getMember());
		return ResponseUtil.success(null);
	}

	// 댓글 수정
	@PutMapping("/comments/{commentId}")
	public ResponseEntity<CommonResponse<Void>> updateComment(
		@PathVariable Long commentId,
		@RequestBody CommentRequestDTO dto,
		@AuthenticationPrincipal MemberDetails loginUser
	) {
		commentService.updateComment(
			commentId,
			dto.getContent(),
			loginUser.getMember().getId()
		);

		return ResponseUtil.success(null);
	}

	// 댓글 삭제
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<CommonResponse<Void>> deleteComment(
		@PathVariable Long commentId,
		@AuthenticationPrincipal MemberDetails loginUser
	) {
		commentService.deleteComment(
			commentId,
			loginUser.getMember().getId()
		);

		return ResponseUtil.success(null);
	}

	// 내가 쓴 댓글 목록 조회
	@GetMapping("/my-comments")
	public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> getMyComments(
		@AuthenticationPrincipal MemberDetails loginUser) {
		List<CommunityPostResponseListDTO> comments = commentService.getMyComments(loginUser.getMember());

		return ResponseUtil.success(comments);
	}
}