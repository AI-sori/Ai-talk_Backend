package com.example.aitalk.community;

import com.example.aitalk.member.Member;
import com.example.aitalk.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;

    public CommunityPostResponseDTO createPost(CommunityPostRequestDTO request, Long id) {
        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        CommunityPost post = new CommunityPost();
        post.setMember(member);
        post.setCategory(request.getCategory());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImage(request.getImage());

        CommunityPost saved = communityPostRepository.save(post);

        return new CommunityPostResponseDTO(
                saved.getId(),
                member.getNickname(),
                saved.getCategory(),
                saved.getTitle(),
                saved.getContent(),
                saved.getImage()
        );
    }
}
