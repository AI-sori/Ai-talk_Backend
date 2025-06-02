package com.example.aitalk.community;

import com.example.aitalk.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    List<CommunityPost> findByMember(Member member);

    List<CommunityPost> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByIdDesc(
            String titleKeyword, String contentKeyword
    );

    @Query("SELECT p FROM CommunityPost p " +
            "WHERE (LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND LOWER(p.category) = LOWER(:category) " +
            "ORDER BY p.id DESC")
    List<CommunityPost> searchByKeywordAndCategory(@Param("keyword") String keyword,
                                                   @Param("category") String category);
}
