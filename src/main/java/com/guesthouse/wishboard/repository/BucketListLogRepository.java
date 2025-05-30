package com.guesthouse.wishboard.repository;

import com.guesthouse.wishboard.entity.BucketList_log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BucketListLogRepository extends JpaRepository<BucketList_log, Long> {
    List<BucketList_log> findByBucketIdOrderByCreatedAtAsc(Long bucketId);
    BucketList_log findTopByBucketIdAndCreatedAtLessThanOrderByCreatedAtDesc(Long bucketId, Date createdAt);
    BucketList_log findTopByBucketIdAndCreatedAtGreaterThanOrderByCreatedAtAsc(Long bucketId, Date createdAt);
}
