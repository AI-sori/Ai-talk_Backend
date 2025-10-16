package com.example.aitalk.domain.community.comment;

import com.example.aitalk.api.exception.BusinessException;
import com.example.aitalk.api.exception.ErrorCode;
import com.example.aitalk.domain.community.comment.dto.CommentRequestDTO;
import com.example.aitalk.domain.community.post.CommunityPost;
import com.example.aitalk.domain.community.post.CommunityPostRepository;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseListDTO;
import com.example.aitalk.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityPostRepository postRepository;

    // 댓글 생성
    public void createComment(CommentRequestDTO dto, Member member) {
        CommunityPost post = getPostOrThrow(dto.getPostId());

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMember(member);
        comment.setContent(dto.getContent());

        commentRepository.save(comment);
    }

    // 댓글 수정
    public void updateComment(Long commentId, String newContent, Long userId) {
        Comment comment = getCommentOrThrow(commentId);

        validateCommentOwner(comment, userId);

        comment.setContent(newContent);
        commentRepository.save(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = getCommentOrThrow(commentId);

        validateCommentOwner(comment, userId);

        commentRepository.delete(comment);
    }

    // 본인이 작성한 댓글 목록
    public List<CommunityPostResponseListDTO> getMyComments(Member member) {
        List<Comment> comments = commentRepository.findByMember(member);

        return comments.stream()
            .map(Comment::getPost)
            .distinct()
            .sorted((p1, p2) -> Long.compare(p2.getId(), p1.getId())) // ID 내림차순 정렬
            .map(post -> {
                int commentCount = commentRepository.countByPost(post); // 댓글 수
                return new CommunityPostResponseListDTO(
                    post.getId(),
                    post.getMember() != null ? post.getMember().getNickname() : "알 수 없음",
                    post.getCategory(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImage(),
                    post.getLikeCount(),
                    commentCount
                );
            })
            .collect(Collectors.toList());
    }

   // ID를 통한 Comment 엔티티 조회
    private Comment getCommentOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));
    }

    // ID를 통한 CommunityPost 엔티티 조회
    private CommunityPost getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
    }

    // 댓글 작성자 권한 검증
    private void validateCommentOwner(Comment comment, Long userId) {
        if (!comment.getMember().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.NO_PERMISSION);
        }
    }
}