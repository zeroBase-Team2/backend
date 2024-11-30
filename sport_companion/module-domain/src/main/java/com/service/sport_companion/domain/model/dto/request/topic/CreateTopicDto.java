package com.service.sport_companion.domain.model.dto.request.topic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateTopicDto {

  @JsonProperty("topic")
  private String topic;

  @JsonCreator
  public CreateTopicDto(String topic) {
    this.topic = topic;
  }
}
