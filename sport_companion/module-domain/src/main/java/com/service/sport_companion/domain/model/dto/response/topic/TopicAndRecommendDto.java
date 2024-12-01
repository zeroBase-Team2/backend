package com.service.sport_companion.domain.model.dto.response.topic;

import com.service.sport_companion.domain.entity.CustomTopicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicAndRecommendDto {

  private CustomTopicEntity customTopicEntity;
  private Long recommendCount;
}
