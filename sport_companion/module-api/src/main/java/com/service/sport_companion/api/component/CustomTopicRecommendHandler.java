package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.model.type.RedisKeyType;
import com.service.sport_companion.domain.repository.CustomTopicRecommendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomTopicRecommendHandler {

  private final CustomTopicRecommendRepository customTopicRecommendRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  // topicId의 주제에 userId 사용자가 추천한 적 있는지 조회
  public boolean existsTopicRecommend(Long userId, Long topicId) {
    return customTopicRecommendRepository
      .existsByCustomTopic_CustomTopicIdAndUsersEntity_UserId(topicId, userId);
  }

  // 사용자 주제 추천 기록 저장
  public void saveByUserIdAndTopicId(Long userId, Long topicId) {
    customTopicRecommendRepository.saveByUserIdAndTopicId(userId, topicId);
  }

  // (Redis) 주제 추천수를 1 증가한다.
  public Long updateTopicRecommendAdd1(Long topicId) {
    return redisTemplate.opsForValue().increment(
      RedisKeyType.TOPIC_RECOMMEND.getKey() + topicId.toString());
  }
}
