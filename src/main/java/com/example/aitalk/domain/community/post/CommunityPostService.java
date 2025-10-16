package com.example.aitalk.domain.community.post;

import com.example.aitalk.api.exception.BusinessException;
import com.example.aitalk.api.exception.ErrorCode;
import com.example.aitalk.domain.community.comment.CommentRepository;
import com.example.aitalk.domain.community.comment.dto.CommentResponseDTO;
import com.example.aitalk.domain.community.like.Like;
import com.example.aitalk.domain.community.like.LikeRepository;
import com.example.aitalk.domain.community.post.dto.CommunityPostRequestDTO;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseDTO;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseListDTO;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.member.MemberRepository;
import com.example.aitalk.infra.s3.S3Uploader;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final S3Uploader s3Uploader;

    public void createPost(CommunityPostRequestDTO dto, Long userId) throws IOException {
        // userId로 Member 객체를 조회
        validateUserLoggedIn(userId);
        Member member = getMemberOrThrow(userId);

        CommunityPost post = new CommunityPost();
        post.setMember(member);
        post.setCategory(dto.getCategory());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = s3Uploader.upload(dto.getImage());
            post.setImage(imageUrl);
        }

        communityPostRepository.save(post);
    }

    // 단건 조회 후 DTO 변환
    @Transactional(readOnly = true)
    public CommunityPostResponseDTO getPostById(Long id, Member loginUser) {
        CommunityPost post = getPostOrThrow(id);

        boolean liked = false;
        if (loginUser != null) {
            liked = likeRepository.existsByMemberAndPost(loginUser, post);
        }

        return convertToResponseDTO(post, true, liked);
    }

    // 페이징 목록 조회 후 DTO 변환
    public List<CommunityPostResponseListDTO> getAllPosts(Sort sort) {
        List<CommunityPost> postList = communityPostRepository.findAll(sort);

        return postList.stream()
                .map(post -> {
                    int commentCount = commentRepository.countByPost(post);  // 댓글 수 조회
                    return convertToListDTO(post, commentCount);              // 댓글 수만 포함
                })
                .toList();
    }
    private CommunityPostResponseListDTO convertToListDTO(CommunityPost post, int commentCount) {
        String nickname = post.getMember() != null ? post.getMember().getNickname() : "알 수 없음";

        return new CommunityPostResponseListDTO(
                post.getId(),
                nickname,
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getImage(),
                post.getLikeCount(),
                commentCount
        );
    }

    // 변환 메서드
    private CommunityPostResponseDTO convertToResponseDTO(CommunityPost post, boolean includeComments, boolean liked) {
        Member writer = post.getMember();
        String nickname = writer != null ? writer.getNickname() : "알 수 없음";
        Long userId = writer != null ? writer.getId() : null;

        List<CommentResponseDTO> commentDTOs = null;
        if (includeComments) {
            commentDTOs = commentRepository.findByPostId(post.getId()).stream()
                    .map(comment -> new CommentResponseDTO(
                            comment.getId(),
                            comment.getMember().getNickname(),
                            comment.getMember().getId(),
                            comment.getContent(),
                            comment.getCreatedAt()
                    ))
                    .toList();
        }

        return new CommunityPostResponseDTO(
                post.getId(),
                nickname,
                userId,
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getImage(),
                post.getLikeCount(),
                commentDTOs,
                liked
        );
    }

    // 게시글 수정
    public void updatePost(Long postId, CommunityPostRequestDTO dto, Long userId) throws IOException {
        validateUserLoggedIn(userId);

        CommunityPost post = getPostOrThrow(postId);

        validatePostOwner(post, userId);

        post.setCategory(dto.getCategory());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = s3Uploader.upload(dto.getImage());
            post.setImage(imageUrl);
        }

        communityPostRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long postId, Long userId) {
        validateUserLoggedIn(userId);

        CommunityPost post = getPostOrThrow(postId);

        validatePostOwner(post, userId);

        communityPostRepository.delete(post);
    }

    // 본인이 작성한 게시글 목록
    @Transactional(readOnly = true)
    public List<CommunityPostResponseListDTO> getMyPosts(Member member) {

        Member logInMember = getLoggedInMemberOrThrow(member);

        List<CommunityPost> posts = communityPostRepository.findByMember(logInMember).stream()
                .sorted(Comparator.comparing(CommunityPost::getId).reversed()) // ID 내림차순 정렬
                .toList();

        return posts.stream()
                .map(post -> {
                    int commentCount = commentRepository.countByPost(post);
                    return convertToListDTO(post, commentCount);
                })
                .collect(Collectors.toList());
    }

    // 좋아요 기능
    public void likePost(Long postId, Member member) {

        Member logInMember = getLoggedInMemberOrThrow(member);

        CommunityPost post = getPostOrThrow(postId);

        if (likeRepository.existsByMemberAndPost(logInMember, post)) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED);
        }

        likeRepository.save(new Like(logInMember, post));
    }

    // 좋아요 취소
    public void unlikePost(Long postId, Member member) {
        Member logInMember = getLoggedInMemberOrThrow(member);

        CommunityPost post = getPostOrThrow(postId);

        Like like = likeRepository.findByMemberAndPost(logInMember, post)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_LIKED));

        likeRepository.delete(like);
    }

    @Transactional(readOnly = true)
    public List<CommunityPostResponseListDTO> getLikedPosts(Member member) {

        Member logInMember = getLoggedInMemberOrThrow(member);

        List<Like> likes = likeRepository.findByMember(logInMember);

        return likes.stream()
                .map(Like::getPost)
                .sorted(Comparator.comparing(CommunityPost::getId).reversed())  // ID 기준 내림차순 정렬
                .map(post -> {
                    int commentCount = commentRepository.countByPost(post);     // 댓글 수 조회
                    return convertToListDTO(post, commentCount);                // 리스트용 DTO로 변환
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommunityPostResponseListDTO> searchPosts(String keyword) {
        List<CommunityPost> posts = communityPostRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByIdDesc(keyword, keyword);

        return posts.stream()
                .map(post -> {
                    int commentCount = commentRepository.countByPost(post);
                    return convertToListDTO(post, commentCount);
                })
                .collect(Collectors.toList());
    }

    // 사용자 인증(로그인) 유효성 검증
    private void validateUserLoggedIn(Long userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
    }

    // ID를 통한 Member 엔티티 조회
    private Member getMemberOrThrow(Long userId) {
        return memberRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

    // ID를 통한 CommunityPost 엔티티 조회
    private CommunityPost getPostOrThrow(Long postId) {
        return communityPostRepository.findById(postId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
    }

    // 작성자 권한 검증
    private void validatePostOwner(CommunityPost post, Long userId) {
        if (!post.getMember().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.NO_PERMISSION);
        }
    }

    // Member 객체 기반 null 인증 검증
    private Member getLoggedInMemberOrThrow(Member member) {
        if (member == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
        return member;
    }
}
