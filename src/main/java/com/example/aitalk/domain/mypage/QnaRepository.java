package com.example.aitalk.domain.mypage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aitalk.domain.member.Member;

public interface QnaRepository extends JpaRepository<Qna, Long> {
	List<Qna> findByMember(Member member);
}
