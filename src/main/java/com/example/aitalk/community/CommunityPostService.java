package com.example.aitalk.community;

import com.example.aitalk.community.comment.Comment;
import com.example.aitalk.community.comment.CommentRepository;
import com.example.aitalk.community.comment.CommentResponseDTO;
import com.example.aitalk.community.like.Like;
import com.example.aitalk.community.like.LikeRepository;
import com.example.aitalk.member.Member;
import com.example.aitalk.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository; // ✅ 댓글 리포지토리 추가

    public void createPost(CommunityPostRequestDTO dto, Long userId) {
        // userId로 Member 객체를 조회
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        CommunityPost post = new CommunityPost();
        post.setMember(member); // ✅ 이제 Member 객체가 제대로 들어감
        post.setCategory(dto.getCategory());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImage(dto.getImage());

        communityPostRepository.save(post);
    }

    // ✅ 단건 조회 후 DTO 변환
    public CommunityPostResponseDTO getPostById(Long id) {
        CommunityPost post = communityPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 없습니다: " + id));

        return convertToResponseDTO(post);
    }

    // ✅ 페이징 목록 조회 후 DTO 변환
    public Page<CommunityPostResponseDTO> getPosts(Pageable pageable) {
        return communityPostRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }

    // ✅ 변환 메서드
    private CommunityPostResponseDTO convertToResponseDTO(CommunityPost post) {
        String nickname = post.getMember() != null ? post.getMember().getNickname() : "알 수 없음";

        // ✅ 댓글 리스트 조회
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        List<CommentResponseDTO> commentDTOs = comments.stream()
                .map(comment -> new CommentResponseDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getMember().getNickname(),
                        comment.getCreatedAt()
                ))
                .toList();

        return new CommunityPostResponseDTO(
                post.getId(),
                nickname,
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getImage(),
                post.getLikeCount()
        );
    }

    public void updatePost(Long postId, CommunityPostRequestDTO dto, Long userId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        post.setCategory(dto.getCategory());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImage(dto.getImage());

        communityPostRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long postId, Long userId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        communityPostRepository.delete(post);
    }

    // 본인이 작성한 게시글 목록
    public List<MyPagePostResponseDTO> getMyPosts(Member member) {
        List<CommunityPost> posts = communityPostRepository.findByMember(member);
        return posts.stream()
                .map(post -> MyPagePostResponseDTO.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .category(post.getCategory())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }


    private final LikeRepository likeRepository;

    public void likePost(Long postId, Member member) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (likeRepository.existsByMemberAndPost(member, post)) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        }

        likeRepository.save(new Like(member, post));
    }

    public void unlikePost(Long postId, Member member) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Like like = likeRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다."));

        likeRepository.delete(like);
    }

    public List<CommunityPostResponseDTO> getLikedPosts(Member member) {
        List<Like> likes = likeRepository.findByMember(member);
        return likes.stream()
                .map(Like::getPost)
                .map(this::convertToResponseDTO)
                .toList();
    }
}
