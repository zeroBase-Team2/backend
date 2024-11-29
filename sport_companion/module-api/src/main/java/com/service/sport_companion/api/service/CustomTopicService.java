package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.request.topic.CreateTopicDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;

public interface CustomTopicService {

  ResultResponse<Void> createTopic(Long userId, CreateTopicDto createTopicDto);

  ResultResponse<Void> deleteTopic(Long userId, Long topicId);
}
