package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.CustomTopicRecommendEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomTopicRecommendRepository extends JpaRepository<CustomTopicRecommendEntity, Long> {

  boolean existsByCustomTopic_CustomTopicIdAndUsersEntity_UserId(Long customTopicId, Long usersId);

  @Modifying
  @Query(value = "INSERT "
    + "INTO custom_topic_recommend(users_id, custom_topic_id) "
    + "VALUES(:userId, :topicId)", nativeQuery = true)
  void saveByUserIdAndTopicId(@Param("userId") Long userId, @Param("topicId") Long topicId);

  Long countByCustomTopic_CustomTopicId(Long topicId);

  void deleteAllByUsersEntity(UsersEntity user);
}
