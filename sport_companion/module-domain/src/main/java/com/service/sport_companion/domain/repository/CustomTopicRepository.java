package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.CustomTopicEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomTopicRepository extends JpaRepository<CustomTopicEntity, Long> {

  Page<CustomTopicEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

  List<CustomTopicEntity> findTop5ByCreatedAtAfterOrderByVoteCountDesc(LocalDateTime createdAt);
}
