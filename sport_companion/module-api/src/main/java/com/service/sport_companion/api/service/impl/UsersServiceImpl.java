package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.api.component.club.ClubsFacade;
import com.service.sport_companion.api.service.UsersService;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.support.SupportClub;
import com.service.sport_companion.domain.model.dto.response.user.UserInfo;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  private final UserHandler userHandler;
  private final ClubsFacade clubsFacade;

  // 사용자 정보 조회
  @Override
  public ResultResponse<UserInfo> getUserInfo(Long userId) {
    UsersEntity user = userHandler.findByUserId(userId);
    ClubsEntity club = clubsFacade.getSupportClubsByUserId(userId);

    UserInfo userInfo = new UserInfo(
        user.getEmail(),
        user.getNickname(),
        club != null ? club.getClubName() : null,
        club != null ? club.getEmblemImg() : null
    );

    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_USERINFO, userInfo);
  }

  // 사용자 정보 수정
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
}
