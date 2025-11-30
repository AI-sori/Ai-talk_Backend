package com.example.aitalk.domain.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findMemberByEmail(String email);

	// fetch join을 활용한 즉시 로딩 -> LEFT JOIN FETCH로 LevelAssessment 기록이 없더라도 Member 가져옴
	@Query("SELECT m FROM Member m LEFT JOIN FETCH m.levelAssessments WHERE m.id = :memberId")
	Optional<Member> findByIdWithAssessments(Long memberId);
}