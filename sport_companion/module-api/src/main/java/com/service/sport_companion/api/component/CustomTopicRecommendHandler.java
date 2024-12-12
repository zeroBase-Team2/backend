package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.repository.CustomTopicRecommendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomTopicRecommendHandler {

  private final CustomTopicRecommendRepository customTopicRecommendRepository;

  // topicId의 주제에 userId 사용자가 추천한 적 있는지 조회
  public boolean existsTopicRecommend(Long userId, Long topicId) {
    if (userId == null) {
      return false;
    }
    return customTopicRecommendRepository
      .existsByCustomTopic_CustomTopicIdAndUsersEntity_UserId(topicId, userId);
  }

  // 사용자 주제 추천 기록 저장
  public void saveByUserIdAndTopicId(Long userId, Long topicId) {
    customTopicRecommendRepository.saveByUserIdAndTopicId(userId, topicId);
  }

  // topicId의 추천수를 조회한다.
  public Long getRecommendCount(Long topicId) {
    return customTopicRecommendRepository.countByCustomTopic_CustomTopicId(topicId);
  }
}
