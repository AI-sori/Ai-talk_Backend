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

}
