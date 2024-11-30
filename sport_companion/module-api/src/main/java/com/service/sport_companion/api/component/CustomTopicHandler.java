package com.service.sport_companion.api.component;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.CustomTopicEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.request.topic.CreateTopicDto;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.repository.CustomTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomTopicHandler {

  private final CustomTopicRepository customTopicRepository;

  public void saveTopic(UsersEntity userEntity, CreateTopicDto createTopicDto) {
    customTopicRepository.save(CustomTopicEntity.builder()
        .topic(createTopicDto.getTopic())
        .users(userEntity)
      .build());
  }

  public CustomTopicEntity findById(Long topicId) {
    return customTopicRepository.findById(topicId)
      .orElseThrow(() -> new GlobalException(FailedResultType.CUSTOM_TOPIC_NOT_FOUND));
  }

  public void deleteTopic(Long topicId) {
    customTopicRepository.deleteById(topicId);
  }

  public Page<CustomTopicEntity> findTopicOrderByCreatedAt(Pageable pageable) {
    return customTopicRepository.findAllByOrderByCreatedAtDesc(pageable);
  }
}
