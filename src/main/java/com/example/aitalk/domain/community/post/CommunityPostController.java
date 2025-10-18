package com.example.aitalk.domain.community.post;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.community.post.dto.CommunityPostRequestDTO;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseDTO;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseListDTO;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.member.MemberDetails;
import com.example.aitalk.global.util.ResponseUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

	private final CommunityPostService communityPostService;

	@PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CommonResponse<Void>> createPost(@RequestPart("dto") CommunityPostRequestDTO dto,
		@RequestPart(value = "image") MultipartFile image, @AuthenticationPrincipal MemberDetails memberDetails) throws
		IOException {
		communityPostService.createPost(dto, image, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	// 게시글 목록 조회
	@GetMapping
	public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> getPosts() {
		Sort sort = Sort.by("id").descending(); // 기본 정렬 조건 (id 내림차순)

		List<CommunityPostResponseListDTO> posts = communityPostService.getAllPosts(sort);

		return ResponseUtil.success(posts);
	}

	// 게시글 단건 조회 (DTO로 반환)
	@GetMapping("/{id}")
	public ResponseEntity<CommonResponse<CommunityPostResponseDTO>> getPost(@PathVariable Long id,
		@AuthenticationPrincipal MemberDetails memberDetails) {
		CommunityPostResponseDTO dto = communityPostService.getPostById(id, memberDetails.getMember());

		return ResponseUtil.success(dto);
	}

	// 게시글 수정
	@PutMapping(value = "/post/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CommonResponse<Void>> updatePost(
		@PathVariable Long id,
		@RequestPart("dto") CommunityPostRequestDTO dto,
		@RequestPart(value = "image") MultipartFile image,
		@AuthenticationPrincipal MemberDetails memberDetails
	) throws IOException {
		communityPostService.updatePost(id, dto, image, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	// 게시글 삭제
	@DeleteMapping("/post/{id}")
	public ResponseEntity<CommonResponse<Void>> deletePost(
		@PathVariable Long id,
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		communityPostService.deletePost(id, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	// 내가 쓴/좋아요 누른 게시글 목록 조회
	@GetMapping({"/my-posts", "/my-likes"})
	public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> getMyList(
		@AuthenticationPrincipal MemberDetails memberDetails,
		HttpServletRequest request
	) {
		List<CommunityPostResponseListDTO> results;
		String requestURI = request.getRequestURI(); // 현재 요청의 URL 경로를 가져오기

		if (requestURI.contains("/my-posts")) {
			results = communityPostService.getMyPosts(memberDetails.getMember());
		} else { // /my-likes
			results = communityPostService.getLikedPosts(memberDetails.getMember());
		}

		return ResponseUtil.success(results);
	}

	// 좋아요 기능
	@PostMapping("/{postId}/like")
	public ResponseEntity<CommonResponse<Void>> likePost(@PathVariable Long postId,
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		communityPostService.likePost(postId, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	@DeleteMapping("/{postId}/like")
	public ResponseEntity<CommonResponse<Void>> unlikePost(@PathVariable Long postId,
		@AuthenticationPrincipal MemberDetails memberDetails
	) {
		communityPostService.unlikePost(postId, memberDetails.getMember());
		return ResponseUtil.success(null);
	}

	// 게시글 검색
	@GetMapping("/search")
	public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> searchPosts(
		@RequestParam("keyword") String keyword
	) {
		List<CommunityPostResponseListDTO> results = communityPostService.searchPosts(keyword);
		return ResponseUtil.success(results);
	}
}
