package com.example.aitalk.domain.community.post;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.community.post.dto.CommunityPostRequestDTO;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseDTO;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseListDTO;
import com.example.aitalk.domain.member.MemberDetails;
import com.example.aitalk.global.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

	private final CommunityPostService communityPostService;

	@PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "post 작성", description = "현재 로그인한 사용자가 post를 작성합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<Void>> createPost(@Valid @ModelAttribute CommunityPostRequestDTO dto,
		@AuthenticationPrincipal MemberDetails memberDetails) throws
		IOException {
		communityPostService.createPost(dto, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	// 게시글 목록 조회
	@GetMapping
	@Operation(summary = "post 목록 조회", description = "작성된 post 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> getPosts() {
		Sort sort = Sort.by("id").descending(); // 기본 정렬 조건 (id 내림차순)

		List<CommunityPostResponseListDTO> posts = communityPostService.getAllPosts(sort);

		return ResponseUtil.success(posts);
	}

	// 게시글 단건 조회 (DTO로 반환)
	@GetMapping("/{id}")
	@Operation(summary = "post 단일 조회", description = "작성된 개별 post를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<CommunityPostResponseDTO>> getPost(@PathVariable Long id,
		@AuthenticationPrincipal MemberDetails memberDetails) {
		CommunityPostResponseDTO dto = communityPostService.getPostById(id, memberDetails.getMember());

		return ResponseUtil.success(dto);
	}

	// 게시글 수정
	@PutMapping(value = "/post/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "post 수정", description = "작성된 post를 수정합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<Void>> updatePost(
		@PathVariable Long id,
		@Valid @ModelAttribute CommunityPostRequestDTO dto,
		@AuthenticationPrincipal MemberDetails memberDetails
	) throws IOException {
		communityPostService.updatePost(id, dto, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	// 게시글 삭제
	@DeleteMapping("/post/{id}")
	@Operation(summary = "post 삭제", description = "작성된 post를 삭제합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<Void>> deletePost(
		@PathVariable Long id,
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		communityPostService.deletePost(id, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	// 내가 쓴 게시글 목록 조회
	@GetMapping("/my-posts")
	@Operation(summary = "내가 작성한 게시글 목록 조회", description = "현재 로그인한 사용자가 작성한 모든 post 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> getMyPosts(
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		List<CommunityPostResponseListDTO> results = communityPostService.getMyPosts(memberDetails.getMember());
		return ResponseUtil.success(results);
	}

	// 내가 좋아요 누른 게시글 목록 조회
	@GetMapping("/my-likes")
	@Operation(summary = "좋아요한 post 목록 조회", description = "좋아요한 모든 post 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> getMyLikedPosts(
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		List<CommunityPostResponseListDTO> results = communityPostService.getLikedPosts(memberDetails.getMember());
		return ResponseUtil.success(results);
	}

	// 좋아요 기능
	@PostMapping("/{postId}/like")
	@Operation(summary = "post 좋아요 저장", description = "작성된 post에 좋아요를 저장합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<Void>> likePost(@PathVariable Long postId,
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		communityPostService.likePost(postId, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	@DeleteMapping("/{postId}/like")
	@Operation(summary = "post 좋아요 취소", description = "작성된 post에 좋아요를 취소합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<Void>> unlikePost(@PathVariable Long postId,
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		communityPostService.unlikePost(postId, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	// 게시글 검색
	@GetMapping("/search")
	@Operation(summary = "post 검색", description = "작성된 post를 검색합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> searchPosts(
		@RequestParam("keyword") String keyword
	) {
		List<CommunityPostResponseListDTO> results = communityPostService.searchPosts(keyword);
		return ResponseUtil.success(results);
	}
}
