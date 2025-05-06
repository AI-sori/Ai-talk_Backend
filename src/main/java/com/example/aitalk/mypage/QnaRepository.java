package com.example.aitalk.mypage;

import com.example.aitalk.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByMember(Member member);
}
