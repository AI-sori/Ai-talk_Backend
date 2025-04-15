package com.example.aitalk.community;

import com.example.aitalk.member.Member;
import com.example.aitalk.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;

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

        return new CommunityPostResponseDTO(
                post.getId(),
                nickname,
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getImage()
        );
    }
}
