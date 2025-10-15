package com.example.aitalk.domain.mypage;

import com.example.aitalk.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByMember(Member member);
}
