package com.example.aitalk.domain.community.post;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.domain.community.post.dto.CommunityPostRequestDTO;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseDTO;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseListDTO;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.global.util.ResponseUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    @PostMapping("/post")
    public ResponseEntity<CommonResponse<Void>> createPost(@ModelAttribute CommunityPostRequestDTO dto, HttpSession session) throws
        IOException {
        // 세션에서 로그인된 사용자 꺼내기
        Member loginUser = (Member) session.getAttribute("loginUser");

        communityPostService.createPost(dto, loginUser != null ? loginUser.getId() : null);

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
    public ResponseEntity<CommonResponse<CommunityPostResponseDTO>> getPost(@PathVariable Long id, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        CommunityPostResponseDTO dto = communityPostService.getPostById(id, loginUser);

        return ResponseUtil.success(dto);
    }

    // 게시글 수정
    @PutMapping("/post/{id}")
    public ResponseEntity<CommonResponse<Void>> updatePost(
            @PathVariable Long id,
            @ModelAttribute CommunityPostRequestDTO dto,
            HttpSession session
    ) throws IOException {
        Member loginUser = (Member) session.getAttribute("loginUser");

        communityPostService.updatePost(id, dto, loginUser != null ? loginUser.getId() : null);

        return ResponseUtil.success(null);
    }

    // 게시글 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<CommonResponse<Void>> deletePost(
            @PathVariable Long id,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        communityPostService.deletePost(id, loginUser != null ? loginUser.getId() : null);

        return ResponseUtil.success(null);
    }

    // 내가 쓴 게시글 목록 조회
    @GetMapping("/my-posts")
    public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> getMyPosts(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        List<CommunityPostResponseListDTO> posts = communityPostService.getMyPosts(loginUser);

        return ResponseUtil.success(posts);
    }

    // 좋아요 기능
    @PostMapping("/{postId}/like")
    public ResponseEntity<CommonResponse<Void>> likePost(@PathVariable Long postId, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        communityPostService.likePost(postId, loginUser);

        return ResponseUtil.success(null);
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<CommonResponse<Void>> unlikePost(@PathVariable Long postId, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        communityPostService.unlikePost(postId, loginUser);

        return ResponseUtil.success(null);
    }

    // 내가 좋아요 누른 게시글 목록 조회
    @GetMapping("/my-likes")
    public ResponseEntity<CommonResponse<List<CommunityPostResponseListDTO>>> getMyLikedPosts(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        List<CommunityPostResponseListDTO> likedPosts = communityPostService.getLikedPosts(loginUser);

        return ResponseUtil.success(likedPosts);
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
