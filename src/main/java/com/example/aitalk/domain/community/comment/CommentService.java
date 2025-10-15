package com.example.aitalk.domain.community.comment;

import com.example.aitalk.domain.community.comment.dto.CommentRequestDTO;
import com.example.aitalk.domain.community.comment.dto.CommentResponseDTO;
import com.example.aitalk.domain.community.post.CommunityPost;
import com.example.aitalk.domain.community.post.CommunityPostRepository;
import com.example.aitalk.domain.community.post.dto.CommunityPostResponseListDTO;
import com.example.aitalk.domain.member.Member;
import com.example.aitalk.domain.member.MemberRepository;
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
                        c.getMember().getId(),
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
}
