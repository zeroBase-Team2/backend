package com.service.sport_companion.domain.model.dto.response.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.sport_companion.domain.entity.CustomTopicEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomTopicResponse {

  private Long topicId;

  private String topic;

  private Long recommendCount;

  @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/seoul")
  private LocalDateTime createdAt;

  private boolean isAuthor;

  public static CustomTopicResponse of(CustomTopicEntity customTopicEntity, boolean isAuthor, Long recommendCount) {
    return CustomTopicResponse.builder()
      .topicId(customTopicEntity.getCustomTopicId())
      .topic(customTopicEntity.getTopic())
      .recommendCount(recommendCount)
      .createdAt(customTopicEntity.getCreatedAt())
      .isAuthor(isAuthor)
      .build();
  }
}
