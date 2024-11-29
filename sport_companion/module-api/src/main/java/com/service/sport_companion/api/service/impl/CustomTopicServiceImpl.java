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
}
