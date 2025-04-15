package com.example.aitalk.community;

import com.example.aitalk.member.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<Page<CommunityPost>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CommunityPost> posts = communityPostService.getPosts(pageable);

        return ResponseEntity.ok(posts);
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommunityPost> getPost(@PathVariable Long id) {
        Optional<CommunityPost> post = Optional.ofNullable(communityPostService.getPostById(id));
        return post.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
