package com.service.sport_companion.domain.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RedisKeyType {
  TOPIC_RECOMMEND("topicRecommend:")
  ;

  private final String key;
}
