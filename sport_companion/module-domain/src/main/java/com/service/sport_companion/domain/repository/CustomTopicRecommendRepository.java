package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.CustomTopicRecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomTopicRecommendRepository extends JpaRepository<CustomTopicRecommendEntity, Long> {

}
