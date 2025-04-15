package com.example.aitalk.community.comment;

import com.example.aitalk.community.CommunityPost;
import com.example.aitalk.community.CommunityPostRepository;
import com.example.aitalk.member.Member;
import com.example.aitalk.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityPostRepository postRepository;
    private final MemberRepository memberRepository;

    // 댓글 생성
    public void createComment(CommentRequestDTO dto, Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        CommunityPost post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMember(member);
        comment.setContent(dto.getContent());

        commentRepository.save(comment);
    }

    // 댓글 조회
    public List<CommentResponseDTO> getComments(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(c -> new CommentResponseDTO(
                        c.getId(),
                        c.getMember().getNickname(),
                        c.getContent(),
                        c.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
    // 댓글 수정
    public void updateComment(Long commentId, String newContent, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        if (!comment.getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 수정 가능");
        }

        comment.setContent(newContent);
        commentRepository.save(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        if (!comment.getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 삭제 가능");
        }

        commentRepository.delete(comment);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
}
