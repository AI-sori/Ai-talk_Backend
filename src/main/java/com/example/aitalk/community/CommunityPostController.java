package com.example.aitalk.community;

import com.example.aitalk.member.Member;
import com.example.aitalk.config.Response;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody CommunityPostRequestDTO dto, HttpSession session) {
        // 세션에서 로그인된 사용자 꺼내기
        Member loginUser = (Member) session.getAttribute("loginUser");

        // 로그인되지 않았다면
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        // 게시글 작성
        try {
            // loginUser의 id를 이용해 게시글 작성
            communityPostService.createPost(dto, loginUser.getId());
            return ResponseEntity.ok("게시글 작성 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 작성 실패: " + e.getMessage());
        }
    }


    // 게시글 목록 조회 (페이징)
    @GetMapping
    public ResponseEntity<Page<CommunityPostResponseDTO>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CommunityPostResponseDTO> posts = communityPostService.getPosts(pageable);

        return ResponseEntity.ok(posts);
    }

    // ✅ 게시글 단건 조회 (DTO로 반환)
    @GetMapping("/{id}")
    public ResponseEntity<CommunityPostResponseDTO> getPost(@PathVariable Long id) {
        try {
            CommunityPostResponseDTO dto = communityPostService.getPostById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    // 게시글 수정
    @PutMapping("/post/{id}")
    public ResponseEntity<String> updatePost(
            @PathVariable Long id,
            @RequestBody CommunityPostRequestDTO dto,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        try {
            communityPostService.updatePost(id, dto, loginUser.getId());
            return ResponseEntity.ok("게시글 수정 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자만 수정할 수 있습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 수정 실패: " + e.getMessage());
        }
    }

    // 게시글 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long id,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        try {
            communityPostService.deletePost(id, loginUser.getId());
            return ResponseEntity.ok("게시글 삭제 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자만 삭제할 수 있습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 삭제 실패: " + e.getMessage());
        }
    }

    // 내가 쓴 게시글 목록 조회
    @GetMapping("/my-posts")
    public Response<List<MyPagePostResponseDTO>> getMyPosts(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        List<MyPagePostResponseDTO> posts = communityPostService.getMyPosts(loginUser);
        return Response.success(posts);
    }

    // 좋아요 기능
    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");

        try {
            communityPostService.likePost(postId, loginUser);
            return ResponseEntity.ok("좋아요 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");

        try {
            communityPostService.unlikePost(postId, loginUser);
            return ResponseEntity.ok("좋아요 취소 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my-likes")
    public ResponseEntity<List<CommunityPostResponseDTO>> getMyLikedPosts(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<CommunityPostResponseDTO> likedPosts = communityPostService.getLikedPosts(loginUser);
        return ResponseEntity.ok(likedPosts);
    }
}
