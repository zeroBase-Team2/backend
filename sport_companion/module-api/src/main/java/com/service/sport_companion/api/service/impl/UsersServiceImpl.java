package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.api.service.UsersService;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.user.UserInfo;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  private final UserHandler userHandler;

  @Override
  public ResultResponse<Void> updateUserInfo(Long userId, String nickname) {
    UsersEntity user = userHandler.findByUserId(userId);

    userHandler.saveUser(UsersEntity.builder()
        .userId(user.getUserId())
        .email(user.getEmail())
        .nickname(nickname)
        .provider(user.getProvider())
        .providerId(user.getProviderId())
        .role(user.getRole())
        .createdAt(user.getCreatedAt())
        .build()
    );

    return ResultResponse.of(SuccessResultType.SUCCESS_UPDATE_USERINFO);
  }

  @Override
  public ResultResponse<UserInfo> getUserInfo(Long userId) {
    UsersEntity user = userHandler.findByUserId(userId);

    UserInfo userInfo = new UserInfo(user.getEmail(), user.getNickname());
    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_USERINFO, userInfo);
  }
}
