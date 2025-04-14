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
        // userId를 여기서 사용해서 게시글 작성 처리
        // 예시:
        CommunityPost post = new CommunityPost();
        post.setUserId(userId); // or post.setMemberId(userId);
        post.setCategory(dto.getCategory());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImage(dto.getImage());

        communityPostRepository.save(post);
    }

}
