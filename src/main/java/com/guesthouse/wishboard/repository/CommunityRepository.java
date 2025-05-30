// src/main/java/com/guesthouse/wishboard/repository/CommunityRepository.java
package com.guesthouse.wishboard.repository;

import com.guesthouse.wishboard.dto.CommunitySearchResponse;
import com.guesthouse.wishboard.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    List<Community> findByTitleContainingIgnoreCase(String keyword);

    // 검색용
    // 키워드를 포함하는 하위분류를 찾고, 상위분류별로 묶어 개수까지 리턴
    @Query("""
           SELECT new com.guesthouse.wishboard.dto.CommunitySearchResponse(
                   c.communityType,
                   COUNT(c)
           )
           FROM Community c
           WHERE LOWER(c.communityDiversity) LIKE LOWER(CONCAT('%', :keyword, '%'))
           GROUP BY c.communityType
           """)
    List<CommunitySearchResponse> findCommunitiesBySubKeyword(@Param("keyword") String keyword);


    /**
     * 커뮤니티별(communityType) + 게시판별(boardType) 목록 조회
     * boardType 이 null 또는 "" 이면 ‘전체’로 간주.
     */
    @Query("""
           SELECT c
             FROM Community c
            WHERE c.communityType = :communityType
              AND (:boardType IS NULL OR :boardType = '' OR c.type = :boardType)
           """)
    Page<Community> findPage(String communityType,
                             String boardType,
                             Pageable pageable);

    /**
     * 게시글 상세 조회 (이미지 eager 로딩)
     */
    @EntityGraph(attributePaths = { "images" })
    Community findByCommunityId(Long communityId);

    /** 상위분류에 속한 게시글 조회 (검색기능) **/
    Page<Community> findByCommunityType(String communityType, Pageable pageable);

    /** 글 불러오기 **/
    Page<Community> findByCommunityTypeAndType(
            String communityType,
            String type,
            Pageable pageable
    );
}
