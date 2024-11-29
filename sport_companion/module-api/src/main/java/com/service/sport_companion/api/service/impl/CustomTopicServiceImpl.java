package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.CustomTopicHandler;
import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.api.service.CustomTopicService;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.CustomTopicEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.request.topic.CreateTopicDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import com.service.sport_companion.domain.model.type.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomTopicServiceImpl implements CustomTopicService {

  private final CustomTopicHandler customTopicHandler;
  private final UserHandler userHandler;

  @Override
  public ResultResponse<Void> createTopic(Long userId, CreateTopicDto createTopicDto) {
    UsersEntity userEntity = userHandler.findByUserId(userId);
    customTopicHandler.saveTopic(userEntity, createTopicDto);

    return ResultResponse.of(SuccessResultType.SUCCESS_CREATE_CUSTOM_TOPIC);
  }

  @Override
  public ResultResponse<Void> deleteTopic(Long userId, Long topicId) {
    UsersEntity userEntity = userHandler.findByUserId(userId);
    CustomTopicEntity customTopicEntity = customTopicHandler.findById(topicId);

    // 작성자이거나 관리자인 경우 삭제할 수 있다.
    if (!customTopicEntity.getUsers().equals(userEntity)
      && !userEntity.getRole().equals(UserRole.ADMIN)) {
      throw new GlobalException(FailedResultType.DELETE_TOPIC_FORBIDDEN);
    }

    customTopicHandler.deleteTopic(topicId);

    return ResultResponse.of(SuccessResultType.SUCCESS_DELETE_CUSTOM_TOPIC);
  }
}
